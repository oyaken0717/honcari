package com.honcari.form;

public class RegisterBookForm {

	/** isbn13コード **/
	private Long isbnId;
	/** 著書名 **/
	private String title;
	/** 著者名 **/
	private String author;
	/**	出版日 **/
	private String publishedDate;
	/**	説明書き **/
	private String description;
	/** ページ数 **/
	private Integer pageCount;
	/**	サムネイルのURL **/
	private String thumbnailPath;
	public Long getIsbnId() {
		return isbnId;
	}
	public void setIsbnId(Long isbnId) {
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
		return "RegisterBookForm [isbnId=" + isbnId + ", title=" + title + ", author=" + author + ", publishedDate="
				+ publishedDate + ", description=" + description + ", pageCount=" + pageCount + ", thumbnailPath="
				+ thumbnailPath + "]";
	}
}
