package com.challenge.scraper.utils;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.challenge.scraper.model.File;

public class Util {

	public static List<File> readZipStream(URL url, String user, String name) throws IOException {

		List<File> list = new ArrayList<>();

		InputStream inputStream = url.openStream();

		ZipInputStream zipIn = new ZipInputStream(inputStream);

		ZipEntry entry;

		while ((entry = zipIn.getNextEntry()) != null) {
			File gitFile = new File();

			if (entry.getSize() > 0) {
				String[] path = entry.getName().split("/");
				String[] file = path[path.length - 1].split("[.]");

				gitFile.setUserGithub(user);
				gitFile.setNameGithub(name);
				gitFile.setDescription(entry.getName());
				gitFile.setExtension(file[file.length - 1]);
				gitFile.setSize(entry.getSize());
				long quantLines = readContents(new FilterInputStream(zipIn) {
					@Override
					public void close() throws IOException {
						zipIn.closeEntry();
					}
				});
				gitFile.setQuantLines(quantLines);

				list.add(gitFile);
			}
		}
		return list;
	}

	private static long readContents(InputStream is) throws IOException {
		long quantLines = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		quantLines = reader.lines().count();
		return quantLines;
	}
}
