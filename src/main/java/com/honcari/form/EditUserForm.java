package com.honcari.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ユーザー情報編集用フォーム.
 * 
 * @author katsuya.fujishima
 *
 */
public class EditUserForm {
	/**	ID */
	private Integer id;
	/**	名前 */
	@NotBlank(message="入力は必須です")
	private String name;
	/**	メールアドレス */
	@NotNull(message="入力は必須です")
	@Email(message="入力形式が不正です")
	private String email;
	/**	パスワード */
	private String password;
	/**	確認用パスワード */
	private String confirmPassword;
	/**	変更前パスワード */
	private String defaultPassword;
	/**	画像 */
	private String imagePath;
	/**	自己紹介文 */
	private String profile;
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
	public String getDefaultPassword() {
		return defaultPassword;
	}
	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
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
		return "EditUserForm [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", defaultPassword=" + defaultPassword + ", imagePath="
				+ imagePath + ", profile=" + profile + "]";
	}
}
