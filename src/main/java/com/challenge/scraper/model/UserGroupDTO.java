package com.challenge.scraper.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserGroupDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private String name;
	private long quantLines;
	private long size;
	private String extension;

	public UserGroupDTO(String user, String name) {
		this.user = user;
		this.name = name;
	}

	public UserGroupDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getQuantLines() {
		return quantLines;
	}

	public void setQuantLines(long quantLines) {
		this.quantLines = quantLines;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public List<UserGroupDTO> toList(List<File> listObj, String user, String name) {
		List<UserGroupDTO> listDto = new ArrayList<>();

		Map<String, Long> resultQuantLines = listObj.stream()
				.collect(Collectors.groupingBy(File::getExtension, Collectors.summingLong(File::getQuantLines)));
		Map<String, Long> resultSise = listObj.stream()
				.collect(Collectors.groupingBy(File::getExtension, Collectors.summingLong(File::getSize)));

		for (String extension : resultQuantLines.keySet()) {
			UserGroupDTO githubUserGroupDTO = new UserGroupDTO(user, name);
			githubUserGroupDTO.setQuantLines(resultQuantLines.get(extension));
			githubUserGroupDTO.setSize(resultSise.get(extension));
			githubUserGroupDTO.setExtension(extension);

			listDto.add(githubUserGroupDTO);
		}

		return listDto;
	}
}
