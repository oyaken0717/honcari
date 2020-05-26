package com.honcari.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

/**
 * ユーザー情報編集用フォーム.
 * 
 * @author katsuya.fujishima
 *
 */
public class EditUserForm {
	/**	ID */
	private Integer userId;
	/**	名前 */
	@NotBlank(message="入力は必須です")
	private String name;
	/**	メールアドレス */
	@NotNull(message="入力は必須です")
	@Email(message="入力形式が不正です")
	private String email;
	/**	新しいパスワード */
	private String password;
	/**	確認用パスワード */
	private String confirmPassword;
	/**	現在のパスワード(ハッシュ化済) */
	private String currentPassword;
	/**	入力された現在のパスワード */
	private String inputCurrentPassword;
	/**	画像 */
	private MultipartFile profileImage;
	/**	画像パス */
	private String imagePath;
	/**	自己紹介文 */
	private String profile;
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getInputCurrentPassword() {
		return inputCurrentPassword;
	}
	public void setInputCurrentPassword(String inputCurrentPassword) {
		this.inputCurrentPassword = inputCurrentPassword;
	}
	public MultipartFile getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
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
	@Override
	public String toString() {
		return "EditUserForm [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", currentPassword=" + currentPassword
				+ ", inputCurrentPassword=" + inputCurrentPassword + ", profileImage=" + profileImage + ", imagePath="
				+ imagePath + ", profile=" + profile + "]";
	}
}
