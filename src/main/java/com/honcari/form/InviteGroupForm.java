package com.honcari.form;

import java.util.List;

public class InviteGroupForm {
	
	private Integer groupId;
	private List<String> userNameList;
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public List<String> getUserNameList() {
		return userNameList;
	}
	public void setUserNameList(List<String> userNameList) {
		this.userNameList = userNameList;
	}
	@Override
	public String toString() {
		return "InviteGroupForm [groupId=" + groupId + ", userNameList=" + userNameList + "]";
	}
	
	


}
