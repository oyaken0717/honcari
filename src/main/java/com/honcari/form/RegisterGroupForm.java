package com.honcari.form;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

/**
 * グループ作成フォーム.
 * 
 * @author yamadadai
 *
 */
public class RegisterGroupForm {
	/** グループ名 */
	@NotBlank(message="入力必須項目です")
	@Size(min=1,max=100, message="グループ名は1文字以上100文字以内で入力してください")
	private String name;
	/** 説明 */
	@NotBlank(message="入力必須項目です")
	private String description;
	/** ユーザー名のリスト */
	private List<String> userNameList;
	/** グループステータス */
	private Integer status;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RegisterGroupForm [name=" + name + ", description=" + description + ", userNameList=" + userNameList
				+ ", status=" + status + "]";
	}
	
}
