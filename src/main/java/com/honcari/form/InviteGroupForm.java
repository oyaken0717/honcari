package com.honcari.form;

import java.util.List;

/**
 * グループ招待フォーム.
 * 
 * @author yamaseki
 *
 */
public class InviteGroupForm {
	/** グループid */
	private Integer groupId;
	/** ユーザー名リスト */
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
