package com.honcari.form;

public class RegisterBookForm {

	/** isbn13コード **/
	private Integer isbnId;
	/** 著書名 **/
	private String title;
	/** 著者名 **/
	private String author;
	/**	出版日 **/
	private String published_date;
	/**	説明書き **/
	private String description;
	/** ページ数 **/
	private Integer pageCount;
	/**	サムネイルのURL **/
	private String thumbnailPath;
	public Integer getIsbnId() {
		return isbnId;
	}
	public void setIsbnId(Integer isbnId) {
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
	public String getPublished_date() {
		return published_date;
	}
	public void setPublished_date(String published_date) {
		this.published_date = published_date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	@Override
	public String toString() {
		return "RegisterBookForm [isbnId=" + isbnId + ", title=" + title + ", author=" + author + ", published_date="
				+ published_date + ", description=" + description + ", pageCount=" + pageCount + ", thumbnailPath="
				+ thumbnailPath + "]";
	}
}
