package com.honcari.domain;

import java.util.List;

/**
 * ユーザードメイン.
 * 
 * @author katsuya.fujishima
 *
 */
public class User {
	/**	ID */
	private Integer userId;
	/**	名前 */
	private String name;
	/**	メールアドレス */
	private String email;
	/**	パスワード */
	private String password;
	/**	画像パス */
	private String imagePath;
	/**	プロフィール文 */
	private String profile;
	/**	ステータス */
	private Integer status;
	/**	所有している本リスト */
	private List<OwnedBookInfo> ownedBookInfoList;
	/**	所属しているグループリスト */
	private List<Group>groupList;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<OwnedBookInfo> getOwnedBookInfoList() {
		return ownedBookInfoList;
	}
	public void setOwnedBookInfoList(List<OwnedBookInfo> ownedBookInfoList) {
		this.ownedBookInfoList = ownedBookInfoList;
	}
	public List<Group> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", imagePath=" + imagePath + ", profile=" + profile + ", status=" + status + ", ownedBookInfoList="
				+ ownedBookInfoList + ", groupList=" + groupList + "]";
	}
}
