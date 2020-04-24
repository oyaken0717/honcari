package com.honcari.domain;

import com.honcari.common.BookStatusEnum;

/**
 * 本の所有者情報を管理するエンティティ.
 * 
 * @author shumpei
 *
 */
public class BookOwner {

	/** ID */
	private Integer bookOwnerId;
	/** ユーザーID */
	private Integer userId;
	/** 本ID */
	private Integer bookId;
	/** カテゴリーID */
	private Integer categoryId;
	/** 貸出状況 */
	private Integer bookStatus;
	/** コメント */
	private String comment;
	/** ユーザー */
	private User user;
	/** 本 */
	private Book book;
	/** カテゴリー */
	private Category category;

	public BookOwner() {
		// TODO Auto-generated constructor stub
	}

	public BookOwner(Integer bookOwnerId, Integer userId, Integer bookId, Integer categoryId, Integer bookStatus,
			String comment, User user, Book book, Category category) {
		super();
		this.bookOwnerId = bookOwnerId;
		this.userId = userId;
		this.bookId = bookId;
		this.categoryId = categoryId;
		this.bookStatus = bookStatus;
		this.comment = comment;
		this.user = user;
		this.book = book;
		this.category = category;
	}

	/**
	 * 数字から貸出状況を取得する.
	 * 
	 * @return 貸出状況
	 */
	public String getBookLendingStatus() {
		return BookStatusEnum.of(bookStatus).getKey();
	}

	public Integer getBookOwnerId() {
		return bookOwnerId;
	}

	public void setBookOwnerId(Integer bookOwnerId) {
		this.bookOwnerId = bookOwnerId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "BookOwner [bookOwnerId=" + bookOwnerId + ", userId=" + userId + ", bookId=" + bookId + ", categoryId="
				+ categoryId + ", bookStatus=" + bookStatus + ", comment=" + comment + ", user=" + user + ", book="
				+ book + ", category=" + category + "]";
	}

}
