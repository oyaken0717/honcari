package com.honcari.domain;

import java.util.Date;

import com.honcari.common.RentalStatusEnum;

public class BookRental {
	
	/** ID */
	private Integer bookRentalId;
	/** 貸し手ユーザーID */
	private Integer lendUserId;
	/** 借り手ユーザーID */
	private Integer borrowUserId;
	/** 本ID */
	private Integer bookId;
	/** 貸し借り状況 */
	private Integer rentalStatus;
	/** 貸出期限 */
	private Date deadline;
	/** 貸し手ユーザー */
	private User lendUser;
	/** 借り手ユーザー */
	private User borrowUser;
	/** 本 */
	private Book book;

	public BookRental() {
	}

	public BookRental(Integer bookRentalId, Integer lendUserId, Integer borrowUserId, Integer bookId,
			Integer rentalStatus, Date deadline, User lendUser, User borrowUser, Book book) {
		super();
		this.bookRentalId = bookRentalId;
		this.lendUserId = lendUserId;
		this.borrowUserId = borrowUserId;
		this.bookId = bookId;
		this.rentalStatus = rentalStatus;
		this.deadline = deadline;
		this.lendUser = lendUser;
		this.borrowUser = borrowUser;
		this.book = book;
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

	public Integer getLendUserId() {
		return lendUserId;
	}

	public void setLendUserId(Integer lendUserId) {
		this.lendUserId = lendUserId;
	}

	public Integer getBorrowUserId() {
		return borrowUserId;
	}

	public void setBorrowUserId(Integer borrowUserId) {
		this.borrowUserId = borrowUserId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
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

	public User getLendUser() {
		return lendUser;
	}

	public void setLendUser(User lendUser) {
		this.lendUser = lendUser;
	}

	public User getBorrowUser() {
		return borrowUser;
	}

	public void setBorrowUser(User borrowUser) {
		this.borrowUser = borrowUser;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "BookRental [bookRentalId=" + bookRentalId + ", lendUserId=" + lendUserId + ", borrowUserId="
				+ borrowUserId + ", bookId=" + bookId + ", rentalStatus=" + rentalStatus + ", deadline=" + deadline
				+ ", lendUser=" + lendUser + ", borrowUser=" + borrowUser + ", book=" + book + "]";
	}

}
