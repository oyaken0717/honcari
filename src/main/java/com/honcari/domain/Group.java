package com.honcari.domain;

import java.util.List;

public class Group {

	private Integer id;
	private String name;
	private String description;
<<<<<<< HEAD
	private Integer userId;
=======
	private Integer ownerUserId;
	private Boolean isPrivate;
	private Boolean deleted;
>>>>>>> origin/develop
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
<<<<<<< HEAD
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
=======

	public Integer getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Integer ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

>>>>>>> origin/develop
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
<<<<<<< HEAD
		return "Group [id=" + id + ", name=" + name + ", description=" + description + ", userId=" + userId
				+ ", userList=" + userList + "]";
	}
	
	
	
	
	
	
	
	
=======
		return "Group [id=" + id + ", name=" + name + ", description=" + description + ", ownerUserId=" + ownerUserId
				+ ", isPrivate=" + isPrivate + ", deleted=" + deleted + ", userList=" + userList + "]";
	}
>>>>>>> origin/develop

}
