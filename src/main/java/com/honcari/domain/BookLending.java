package com.honcari.domain;

import java.util.Date;

public class BookLending {
	
	private Integer bookLendingId;
	private Integer lendUserId;
	private Integer borrowUserId;
	private Integer bookId;
	private Date deadline;
	private Integer lendingStatus;
	private User lendUser;
	private User borrowUser;
	private Book book;
	
	/**
	 * 数字から貸出状況を取得する.
	 * 
	 * @return 貸出状況
	 */
	public String getBookLendingStatus() {
		return LendingStatusEnum.of(lendingStatus).getKey();
	}
	public Integer getBookLendingId() {
		return bookLendingId;
	}
	public void setBookLendingId(Integer bookLendingId) {
		this.bookLendingId = bookLendingId;
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
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public Integer getLendingStatus() {
		return lendingStatus;
	}
	public void setLendingStatus(Integer lendingStatus) {
		this.lendingStatus = lendingStatus;
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
		return "BookLending [bookLendingId=" + bookLendingId + ", lendUserId=" + lendUserId + ", borrowUserId="
				+ borrowUserId + ", bookId=" + bookId + ", deadline=" + deadline + ", lendingStatus=" + lendingStatus
				+ ", lendUser=" + lendUser + ", borrowUser=" + borrowUser + ", book=" + book + "]";
	}

	
	
	
}
