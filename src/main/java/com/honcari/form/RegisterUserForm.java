package com.honcari.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegisterUserForm {
	@NotBlank(message="入力必須項目です")
	private String name;
	@NotBlank(message="入力必須項目です")
	private String email;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]){8,100}$", message="半角英数字8桁以上で設定してください")
	@NotBlank(message="入力必須項目です")
	private String password;
	@NotBlank(message="入力必須項目です")
	private String confirmPassword;
	
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
	@Override
	public String toString() {
		return "RegisterUserForm [name=" + name + ", email=" + email + ", password=" + password + ", confirmPassword="
				+ confirmPassword + "]";
	}
	
	

}
