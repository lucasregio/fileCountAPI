package com.challenge.scraper.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class GitConfig extends Config{
	public GitConfig () {
//        this.name = "github";
//        this.url = "https://github.com/{{user}}/{{project}}/tree/{{branch}}{{path}}";
//        this.rawUrl = "https://raw.githubusercontent.com/{{user}}/{{project}}/{{branch}}{{path}}";
//        this.tokenizer = Pattern.compile("com/([^/]*)/([^/]*)(/tree/([^/]*))?");
//        this.scraper = (String ml) -> {
//            final List<Path> listPath = new ArrayList<>();
//
//            MLQuery.from(ml).find("[role='row']").filter(".Box-row").each((el, _i) -> {
//                MLQuery $el = new MLQuery(el);
//
//                String pathTypeStr = $el.find("svg").attr("aria-label");
//                if (pathTypeStr.isEmpty()) { return; }
//                PathType pathType = PathType.parseLabel(pathTypeStr);
//
//                String pathName = $el.find(".js-navigation-open").text();
//                Path path = PathFactory.create(pathType);
//                path.setName(pathName);
//
//                listPath.add(path);
//            });
//
//            return listPath;
//        };
    }
	
}
