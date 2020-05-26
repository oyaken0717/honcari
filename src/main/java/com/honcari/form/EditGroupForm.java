package com.honcari.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

/**
 * グループ内容編集フォーム.
 * 
 * @author yamaseki
 *
 */
public class EditGroupForm {
	
	/** グループid */
	private Integer groupId;
	/** 名前 */
	@NotBlank(message="入力必須項目です")
	@Size(min=2,max=50, message="グループ名は2文字以上50文字以内で入力してください")
	private String name;
	/** グループの説明 */
	@Size(min=10,max=500, message="グループの説明は10文字以上500文字以内で入力してください")
	@NotBlank(message="入力必須項目です")
	private String description;
	/** グループ作成者のid */
	private Integer ownerUserId;
	/** グループの状態 */
	private Integer groupStatus;
	/** グループオーナー権限委任リクエスト */
	private Integer requestedOwnerUserId;
	/** グループオーナー権限委任リクエストを送るユーザー名 */
	private String userName;
	/** グループ画像 */
	private MultipartFile groupImage;
	/** グループ画像 */
	private String imagePath;

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

	public Integer getRequestedOwnerUserId() {
		return requestedOwnerUserId;
	}

	public void setRequestedOwnerUserId(Integer requestedOwnerUserId) {
		this.requestedOwnerUserId = requestedOwnerUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public MultipartFile getGroupImage() {
		return groupImage;
	}

	public void setGroupImage(MultipartFile groupImage) {
		this.groupImage = groupImage;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "EditGroupForm [groupId=" + groupId + ", name=" + name + ", description=" + description
				+ ", ownerUserId=" + ownerUserId + ", groupStatus=" + groupStatus + ", requestedOwnerUserId="
				+ requestedOwnerUserId + ", userName=" + userName + ", groupImage=" + groupImage + ", imagePath="
				+ imagePath + "]";
	}
	
	
}
