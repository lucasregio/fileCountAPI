package com.challenge.scraper.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class File {
	private String extension;
    private Integer linesNumber;
    private Integer bytes;
}
