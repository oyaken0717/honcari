package com.honcari.domain;

import java.util.List;

public class Group {
	
	private Integer id;
	private String name;
	private String description;
	private Integer userId;
	private List<User> userList;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", description=" + description + ", userId=" + userId
				+ ", userList=" + userList + "]";
	}
	
	
	
	
	
	
	
	

}
