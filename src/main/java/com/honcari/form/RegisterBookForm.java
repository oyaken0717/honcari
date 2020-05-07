package com.honcari.form;

public class RegisterBookForm {

	/** isbn13コード **/
	private String isbnId;
	/** 著書名 **/
	private String title;
	/** 著者名 **/
	private String author;
	/**	出版日 **/
	private String publishedDate;
	/**	説明書き **/
	private String description;
	/** ページ数 **/
	private String pageCount;
	/**	サムネイルのURL **/
	private String thumbnailPath;
	/**	カテゴリid **/
	private String categoryId;
	/** コメント **/
	private String comment;
	/**	貸出状況 */
	private String bookStatus;
	public String getIsbnId() {
		return isbnId;
	}
	public void setIsbnId(String isbnId) {
		this.isbnId = isbnId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
	@Override
	public String toString() {
		return "RegisterBookForm [isbnId=" + isbnId + ", title=" + title + ", author=" + author + ", publishedDate="
				+ publishedDate + ", description=" + description + ", pageCount=" + pageCount + ", thumbnailPath="
				+ thumbnailPath + ", categoryId=" + categoryId + ", comment=" + comment + ", bookStatus=" + bookStatus
				+ "]";
	}
}