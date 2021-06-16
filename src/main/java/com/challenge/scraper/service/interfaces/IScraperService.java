package com.challenge.scraper.service.interfaces;

import java.util.List;

import com.challenge.scraper.model.File;

public interface IScraperService {

	public List<File> findAll();

	public List<File> findAllFillesInARepository();

	public List<File> loadFromRepository(String user, String name);

	public List<File> findByRepository(String user, String name);

	public List<File> search(String user, String name);

}
