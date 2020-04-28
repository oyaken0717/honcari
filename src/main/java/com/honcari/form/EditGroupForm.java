package com.honcari.form;

import javax.validation.constraints.NotBlank;

public class EditGroupForm {
	private Integer groupId;
	@NotBlank(message="入力必須項目です")
	private String name;
	@NotBlank(message="入力必須項目です")
	private String description;
	private Integer ownerUserId;
	private Integer groupStatus;
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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
	@Override
	public String toString() {
		return "EditGroupForm [groupId=" + groupId + ", name=" + name + ", description=" + description
				+ ", ownerUserId=" + ownerUserId + ", groupStatus=" + groupStatus + "]";
	}
	
	
	
	
	
	
	
	
	
	

}
