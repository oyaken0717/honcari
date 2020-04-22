package com.honcari.form;

import java.util.List;

import javax.validation.constraints.NotBlank;

/**
 * グループ作成フォーム.
 * 
 * @author yamadadai
 *
 */
public class RegisterGroupForm {
	/** グループ名 */
	@NotBlank(message="入力必須項目です")
	private String name;
	/** 説明 */
	@NotBlank(message="入力必須項目です")
	private String description;
	/** ユーザー名のリスト */
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
