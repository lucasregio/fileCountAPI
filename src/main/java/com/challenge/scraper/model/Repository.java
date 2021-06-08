package com.challenge.scraper.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.apache.catalina.core.ApplicationContext;
import org.apache.tomcat.jni.Directory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

import com.challenge.scraper.config.GitConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Repository {
    private GitConfig provider;
    private String user;
    private String project;
    private String branch;
    private Directory workdir;
    @Getter @Setter private boolean retrieveMetadata = false;

    public Repository navigateTo(String dest) {
        if (!existsChildren()) { update(); }

        Path destPath = workdir.getChild(dest);
        workdir = (Directory) destPath;

        return this;
    }

    public Repository navigateTo(Path path) {
        navigateTo(path.getName());
        return this;
    }

    public Path get(String pathName) {
        if (!existsChildren()) { update(); }

        return workdir.getChild(pathName);
    }

    public Map<String, Path> getAll() {
        if (!existsChildren()) { update(); }
        return workdir.getChildren();
    }

    public List<File> getAllFiles() {
        List<File> files = new ArrayList<>();
        getAllFiles(files);
        return files;
    }

    private void getAllFiles(final List<File> files) {
        if (!existsChildren()) { update(); }

        Map<PathType, List<Path>> pathByType = getWorkdir().getChildren().values()
                .stream().collect(Collectors.groupingBy(Path::getType));

        if (pathByType.containsKey(PathType.FILE)) {
            List<File> newFiles = pathByType.get(PathType.FILE).stream()
                    .map(f -> (File) f).collect(Collectors.toList());
            synchronized (files) { files.addAll(newFiles); }
        }

        if (pathByType.containsKey(PathType.DIRECTORY)) {
            pathByType.get(PathType.DIRECTORY).forEach(d -> clone((Directory) d).getAllFiles(files));
        }
    }

    public static Repository parse(String rawRepo) throws ClassNotFoundException {
        Repository repo = new Repository();
        repo.provider = GitProvider.parse(rawRepo);

        Matcher matcher = repo.provider.getTokenizer().matcher(rawRepo);
        if (!matcher.find()) { /*throw new MalformedURLException("rawRepo");*/ }

        repo.user = matcher.group(1);
        repo.project = matcher.group(2);
        repo.branch = matcher.group(4);
        if (repo.branch == null) { repo.branch = "master"; }

        ApplicationContext context = ApplicationContext.getInstance();
        if (context.hasRepository(repo)) {
            return context.getRepository(repo);
        }

        repo.workdir = new Directory("");
        context.setRepository(repo);
        return repo;
    }

    private boolean existsChildren() {
        return !workdir.getChildren().isEmpty();
    }

    public boolean update() {
        HttpRequest req = new HttpRequest();
        HttpResponse response = req.get(provider.getUrl(this, workdir));
        if (response.getStatus().equals(HttpStatus.OK)) {
            List<Path> children = listPath(response.getData());
            workdir.setChildren(children);
            fetchMetadata(children);
            return true;
        }
        return false;
    }

    public void fetchMetadata(List<Path> children) {
        if (!isRetrieveMetadata()) { return; }
        children.stream()
                .filter(c -> c.getType().equals(PathType.FILE))
                .map(c -> (File) c)
                .forEach(file -> {
                    HttpRequest req = new HttpRequest();
                    HttpResponse response = req.get(provider.getRawUrl(this, file));
                    if (!response.getStatus().equals(HttpStatus.OK)) { return; }

                    byte[] data = response.getBytes();
                    file.setBytes(data.length);
                    file.setLinesNumber(count(response.getData()));
        });
    }

    private static int count(String str){
        String[] lines = str.split("\r\n|\r|\n", -1);
        return lines.length;
    }

    private List<Path> listPath(String ml) {
        return provider.getScraper().apply(ml);
    }

    protected Repository clone(Directory workdir) {
        Repository repo = new Repository();
        repo.provider = provider;
        repo.user = user;
        repo.project = project;
        repo.branch = branch;
        repo.workdir = workdir;
        repo.retrieveMetadata = retrieveMetadata;
        return repo;
    }

    public String getKey() {
        return provider.getName() + "|" + user + "|" + project + "|" + branch;
    }
}
