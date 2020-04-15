package com.honcari.domain;

/**
 * 本情報を管理するエンティティ.
 * 
 * @author shumpei
 *
 */
public class Book {
	
	/** ID */
	private Integer id;
	/**	ISBNコード(13) **/
	private Long isbnId;
	/** ユーザーID */
	private Integer userId;
	/** カテゴリーID */
	private Integer categoryId;
	/** タイトル */
	private String title;
	/** 著者 */
	private String author;
	/** 発行日 */
	private String publishedDate;
	/** 説明 */
	private String description;
	/** 総ページ数 */
	private Integer pageCount;
	/** サムネイル画像パス */
	private String thumbnailPath;
	/** 貸出状況 */
	private Integer status;
//	/** ユーザー */
//	private User user;
//	/** カテゴリー */
//	private Category category;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getIsbnId() {
		return isbnId;
	}
	public void setIsbnId(Long isbnId) {
		this.isbnId = isbnId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", isbnId=" + isbnId + ", userId=" + userId + ", categoryId=" + categoryId
				+ ", title=" + title + ", author=" + author + ", publishedDate=" + publishedDate + ", description="
				+ description + ", pageCount=" + pageCount + ", thumbnailPath=" + thumbnailPath + ", status=" + status
				+ "]";
	}

	
}