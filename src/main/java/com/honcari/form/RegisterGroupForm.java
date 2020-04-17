package com.honcari.form;

import java.util.List;

/**
 * グループ作成フォーム.
 * 
 * @author yamadadai
 *
 */
public class RegisterGroupForm {
	/**
	 * グループ名
	 */
	private String name;
	/**
	 * 説明
	 */
	private String description;
	/**
	 * ユーザー名のリスト
	 */
	private List<String> userNameList;

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

	public List<String> getUserNameList() {
		return userNameList;
	}

	public void setUserNameList(List<String> userNameList) {
		this.userNameList = userNameList;
	}

	@Override
	public String toString() {
		return "RegisterGroupForm [name=" + name + ", description=" + description + ", userNameList=" + userNameList
				+ "]";
	}

}
