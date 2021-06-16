package com.challenge.scraper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.scraper.model.File;
import com.challenge.scraper.model.FileDTO;
import com.challenge.scraper.model.UserGroupDTO;
import com.challenge.scraper.service.ScraperService;

@RestController
@RequestMapping(value = "/controller")
public class WebScraperController {

	@RestController
	public class ScrapingController {

		@Autowired
		private ScraperService scraperService;

		@GetMapping
		public ResponseEntity<List<FileDTO>> loadAll() {
			List<File> list = scraperService.findAll();
			List<FileDTO> listDto = new FileDTO().toList(list);
			return ResponseEntity.ok().body(listDto);
		}

		@GetMapping(value = "/find")
		public ResponseEntity<List<UserGroupDTO>> findByRepository(@RequestParam(name = "user") String user,
				@RequestParam(name = "name") String name) {
			List<File> githubUserGroup = scraperService.findByRepository(user, name);
			List<UserGroupDTO> listDto = new UserGroupDTO().toList(githubUserGroup, user, name);
			return ResponseEntity.ok().body(listDto);
		}

		@GetMapping(value = "/load")
		public ResponseEntity<List<UserGroupDTO>> loadByRepository(@RequestParam(name = "user") String user,
				@RequestParam(name = "name") String name) {
			List<File> githubUserGroup = scraperService.loadFromRepository(user, name);
			List<UserGroupDTO> listDto = new UserGroupDTO().toList(githubUserGroup, user, name);
			return ResponseEntity.ok().body(listDto);
		}

	}
}
