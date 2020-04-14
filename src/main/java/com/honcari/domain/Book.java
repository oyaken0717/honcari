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
	/** ユーザーID */
	private Integer userId;
	/** カテゴリーID */
	private Integer categoryId;
	/** タイトル */
	private String title;
	/** 著者 */
	private String author;
	/** 発行日 */
	private String published_date;
	/** 説明 */
	private String description;
	/** 総ページ数 */
	private Integer pageCount;
	/** サムネイル画像パス */
	private String thumbnailPath;
	/** 貸出状況 */
	private Integer status;
	/** ユーザー */
	private User user;
	/** カテゴリー */
	private Category category;

	public Book() {
	}

	public Book(Integer id, Integer userId, Integer categoryId, String title, String author, String published_date,
			String description, Integer pageCount, String thumbnailPath, Integer status, User user, Category category) {
		super();
		this.id = id;
		this.userId = userId;
		this.categoryId = categoryId;
		this.title = title;
		this.author = author;
		this.published_date = published_date;
		this.description = description;
		this.pageCount = pageCount;
		this.thumbnailPath = thumbnailPath;
		this.status = status;
		this.user = user;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
