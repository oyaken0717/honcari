package com.honcari.domain;

import java.util.List;

public class User {
	private Integer id;
	private String name;
	private String email;
	private String password;
	private String imagePath;
	private String profile;
	private List<BookOwner> bookOwnerList;
	private List<Group> groupList;
	private Boolean deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public List<BookOwner> getBookOwnerList() {
		return bookOwnerList;
	}

	public void setBookOwnerList(List<BookOwner> bookOwnerList) {
		this.bookOwnerList = bookOwnerList;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", imagePath="
				+ imagePath + ", profile=" + profile + ", bookOwnerList=" + bookOwnerList + ", groupList=" + groupList
				+ ", deleted=" + deleted + "]";
	}

}
