package com.honcari.form;

import javax.validation.constraints.NotBlank;

/**
 * 貸出リクエストを受け取るフォームクラス.
 * 
 * @author shumpei
 *
 */
public class RentalRequestForm {

	/** 本ID */
	private Integer bookId;
	/** 貸し手ユーザーID */
	private Integer lenderUserId;
	/** 貸出状況 */
	private Integer status;
	/** 貸出期限 */
	@NotBlank(message = "貸出期限を入力してください")
	private String deadline;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getLenderUserId() {
		return lenderUserId;
	}

	public void setLenderUserId(Integer lenderUserId) {
		this.lenderUserId = lenderUserId;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LendingRequestForm [bookId=" + bookId + ", lenderUserId=" + lenderUserId + ", status=" + status
				+ ", deadline=" + deadline + "]";
	}

}
