package com.honcari.domain;

import java.util.List;

public class Group {

	private Integer id;
	private String name;
	private String description;
	private Integer ownerUserId;
	private Integer groupStatus;
	private List<User> userList;
	private User ownerUser;
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
	public Integer getOwnerUserId() {
		return ownerUserId;
	}
	public void setOwnerUserId(Integer ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	public Integer getGroupStatus() {
		return groupStatus;
	}
	public void setGroupStatus(Integer groupStatus) {
		this.groupStatus = groupStatus;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public User getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", description=" + description + ", ownerUserId=" + ownerUserId
				+ ", groupStatus=" + groupStatus + ", userList=" + userList + ", ownerUser=" + ownerUser + "]";
	}

	
}
