package com.challenge.scraper.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.challenge.scraper.model.File;
import com.challenge.scraper.repository.FileRepository;
import com.challenge.scraper.service.interfaces.IScraperService;
import com.challenge.scraper.utils.Util;

public class ScraperService implements IScraperService {

	@Autowired
	private FileRepository fileRepository;

	public List<File> findAll() {
		return fileRepository.findAll();
	}

	/**
	 * This method is used to load repository by name and user from database
	 * 
	 * @param user
	 * @param name
	 * @return
	 */
	public List<File> loadFromRepository(String user, String name) {
		File filter = new File(user, name);
		Example<File> example = Example.of(filter);
		return fileRepository.findAll(example);
	}

	/**
	 * This method is used to find a repository by name and user from GitHub and
	 * insert in database
	 * 
	 * @param user
	 * @param name
	 * @return
	 */
	public List<File> findByRepository(String user, String name) {
		List<File> repository = loadFromRepository(user, name);
		if (repository.isEmpty()) {
			repository = this.search(user, name);
		}
		return repository;
	}

	/**
	 * This method is used to get the files from a repository, downloading by a zip
	 * file
	 * 
	 * @param user
	 * @param name
	 * @return List of Files in a determinate repository
	 */
	public List<File> search(String user, String name) {
		List<File> list = new ArrayList<>();
		try {
			URL url = new URL("https://github.com/" + user + "/" + name + "/archive/master.zip");

			list = Util.readZipStream(url, user, name);

			this.saveAll(list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Transactional
	private List<File> saveAll(List<File> list) {
		return fileRepository.saveAll(list);
	}

	@Override
	public List<File> findAllFillesInARepository() {
		// TODO Auto-generated method stub
		return null;
	}

}
