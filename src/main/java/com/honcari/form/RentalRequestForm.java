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
	private Integer ownedBookInfoVersion;
	/** 貸出期限 */
	@NotBlank(message = "貸出期限を入力してください")
	private String requestDeadline;

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

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getOwnedBookInfoVersion() {
		return ownedBookInfoVersion;
	}

	public void setOwnedBookInfoVersion(Integer ownedBookInfoVersion) {
		this.ownedBookInfoVersion = ownedBookInfoVersion;
	}

	public String getRequestDeadline() {
		return requestDeadline;
	}

	public void setRequestDeadline(String requestDeadline) {
		this.requestDeadline = requestDeadline;
	}

	@Override
	public String toString() {
		return "RentalRequestForm [ownedBookInfoId=" + ownedBookInfoId + ", bookStatus=" + bookStatus + ", ownerId="
				+ ownerId + ", ownedBookInfoVersion=" + ownedBookInfoVersion + ", requestDeadline=" + requestDeadline
				+ "]";
	}

}