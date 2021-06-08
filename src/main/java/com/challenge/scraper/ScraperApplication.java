package com.challenge.scraper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScraperApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(ScraperApplication.class, args);
//	}

	
	public static void main(String[] args) throws IOException {
			
		URL url = new URL("http://example.com");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
	}
	
}
