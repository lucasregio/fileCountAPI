package com.challenge.scraper.model;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class FileDTO implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	// Attributes related to Repository
	private String userGithub;
	private String nameGithub;

	// Attributes related to File
	private String description;
	private Long size;
	private Long quantLines;
	private String extension;

	public FileDTO() {
	}

	public FileDTO(File u) {
		this.setId(u.getId());
		this.userGithub = u.getUserGithub();
		this.nameGithub = u.getNameGithub();
		this.description = u.getDescription();
		this.size = u.getSize();
		this.quantLines = u.getQuantLines();
		this.extension = u.getExtension();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getQuantLines() {
		return quantLines;
	}

	public void setQuantLines(Long quantLines) {
		this.quantLines = quantLines;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getUserGithub() {
		return userGithub;
	}

	public void setUserGithub(String userGithub) {
		this.userGithub = userGithub;
	}

	public String getNameGithub() {
		return nameGithub;
	}

	public void setNameGithub(String nameGithub) {
		this.nameGithub = nameGithub;
	}

	public List<FileDTO> toList(List<File> listObj) {
		List<FileDTO> listDto = listObj.stream().map(u -> new FileDTO(u)).collect(Collectors.toList());
		return listDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
