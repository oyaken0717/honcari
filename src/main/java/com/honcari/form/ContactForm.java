package com.honcari.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * お問い合わせ用フォーム.
 * 
 * @author katsuya.fujishima
 *
 */
public class ContactForm {
	@NotBlank(message="入力は必須です")
	private String name;
	@NotBlank(message="入力は必須です")
	@Email(message="入力形式が不正です")
	private String email;
	private String content;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "ContactForm [name=" + name + ", email=" + email + ", content=" + content + "]";
	}
}
