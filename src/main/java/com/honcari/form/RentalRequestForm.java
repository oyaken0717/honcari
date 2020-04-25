package com.honcari.form;

import javax.validation.constraints.NotBlank;

/**
 * 貸出リクエストを受け取るフォームクラス.
 * 
 * @author shumpei
 *
 */
public class RentalRequestForm {

	/** ユーザーが所有する本情報ID */
	private Integer ownedBookInfoId;
	/** 貸出状況 */
	private Integer bookStatus;
	/** 貸出期限 */
	@NotBlank(message = "貸出期限を入力してください")
	private String deadline;

	public Integer getOwnedBookInfoId() {
		return ownedBookInfoId;
	}

	public void setOwnedBookInfoId(Integer ownedBookInfoId) {
		this.ownedBookInfoId = ownedBookInfoId;
	}

	public Integer getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "RentalRequestForm [ownedBookInfoId=" + ownedBookInfoId + ", bookStatus=" + bookStatus + ", deadline="
				+ deadline + "]";
	}

}
