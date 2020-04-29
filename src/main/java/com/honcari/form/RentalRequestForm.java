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
	/** 所有者ID */
	private Integer ownerId;
	/** バージョン */
	private Integer version;
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

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "RentalRequestForm [ownedBookInfoId=" + ownedBookInfoId + ", bookStatus=" + bookStatus + ", ownerId="
				+ ownerId + ", version=" + version + ", deadline=" + deadline + "]";
	}

}
