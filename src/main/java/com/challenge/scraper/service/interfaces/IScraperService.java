package com.challenge.scraper.service.interfaces;

import java.util.List;

import com.challenge.scraper.model.File;

public interface IScraperService {
	
	public List<File> findAllFillesInARepository();
	

}
