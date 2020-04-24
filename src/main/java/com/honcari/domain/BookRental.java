package com.honcari.domain;

import java.util.Date;

import com.honcari.common.RentalStatusEnum;

public class BookRental {

	/** ID */
	private Integer bookRentalId;
	/** 貸し手ユーザーID */
	private Integer bookOwnerId;
	/** 借り手ユーザーID */
	private Integer borrowUserId;
	/** 貸し借り状況 */
	private Integer rentalStatus;
	/** 貸出期限 */
	private Date deadline;
	/** 本所有情報 */
	private BookOwner bookOwner;
	/** 借り手ユーザー */
	private User borrowUser;

	public BookRental() {
	}

	public BookRental(Integer bookRentalId, Integer bookOwnerId, Integer borrowUserId, Integer rentalStatus,
			Date deadline, BookOwner bookOwner, User borrowUser) {
		super();
		this.bookRentalId = bookRentalId;
		this.bookOwnerId = bookOwnerId;
		this.borrowUserId = borrowUserId;
		this.rentalStatus = rentalStatus;
		this.deadline = deadline;
		this.bookOwner = bookOwner;
		this.borrowUser = borrowUser;
	}

	/**
	 * 数字から貸出状況を取得する.
	 * 
	 * @return 貸出状況
	 */
	public String getBookLendingStatus() {
		return RentalStatusEnum.of(rentalStatus).getKey();
	}

	public Integer getBookRentalId() {
		return bookRentalId;
	}

	public void setBookRentalId(Integer bookRentalId) {
		this.bookRentalId = bookRentalId;
	}

	public Integer getBookOwnerId() {
		return bookOwnerId;
	}

	public void setBookOwnerId(Integer bookOwnerId) {
		this.bookOwnerId = bookOwnerId;
	}

	public Integer getBorrowUserId() {
		return borrowUserId;
	}

	public void setBorrowUserId(Integer borrowUserId) {
		this.borrowUserId = borrowUserId;
	}

	public Integer getRentalStatus() {
		return rentalStatus;
	}

	public void setRentalStatus(Integer rentalStatus) {
		this.rentalStatus = rentalStatus;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public BookOwner getBookOwner() {
		return bookOwner;
	}

	public void setBookOwner(BookOwner bookOwner) {
		this.bookOwner = bookOwner;
	}

	public User getBorrowUser() {
		return borrowUser;
	}

	public void setBorrowUser(User borrowUser) {
		this.borrowUser = borrowUser;
	}

	@Override
	public String toString() {
		return "BookRental [bookRentalId=" + bookRentalId + ", bookOwnerId=" + bookOwnerId + ", borrowUserId="
				+ borrowUserId + ", rentalStatus=" + rentalStatus + ", deadline=" + deadline + ", bookOwner="
				+ bookOwner + ", borrowUser=" + borrowUser + "]";
	}

}
