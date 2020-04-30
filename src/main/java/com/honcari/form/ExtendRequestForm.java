package com.honcari.form;

import javax.validation.constraints.NotBlank;

/**
 * 延長申請を受け取るフォームクラス.
 * 
 * @author shumpei
 *
 */
public class ExtendRequestForm {

	/** 貸出情報ID */
	private Integer bookRentalId;
	/** バージョン */
	private Integer bookRentalVersion;
	/** 貸出期限 */
	@NotBlank(message = "延長期限を入力してください")
	private String deadline;
	/** 元々の貸出期限 */
	private String defaultDeadline;

	public Integer getBookRentalId() {
		return bookRentalId;
	}

	public void setBookRentalId(Integer bookRentalId) {
		this.bookRentalId = bookRentalId;
	}

	public Integer getBookRentalVersion() {
		return bookRentalVersion;
	}

	public void setBookRentalVersion(Integer bookRentalVersion) {
		this.bookRentalVersion = bookRentalVersion;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getDefaultDeadline() {
		return defaultDeadline;
	}

	public void setDefaultDeadline(String defaultDeadline) {
		this.defaultDeadline = defaultDeadline;
	}

	@Override
	public String toString() {
		return "ExtendRequestForm [bookRentalId=" + bookRentalId + ", bookRentalVersion=" + bookRentalVersion
				+ ", deadline=" + deadline + ", defaultDeadline=" + defaultDeadline + "]";
	}

}
