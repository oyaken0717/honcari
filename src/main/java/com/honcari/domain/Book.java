package com.honcari.domain;

/**
 * 本情報を管理するエンティティ.
 * 
 * @author shumpei
 *
 */
public class Book {

	/** ID */
	private Integer bookId;
	/** ISBNコード(13) **/
	private String isbnId;
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

	public Book() {
	}

	public Book(Integer bookId, String isbnId, String title, String author, String publishedDate, String description,
			Integer pageCount, String thumbnailPath) {
		super();
		this.bookId = bookId;
		this.isbnId = isbnId;
		this.title = title;
		this.author = author;
		this.publishedDate = publishedDate;
		this.description = description;
		this.pageCount = pageCount;
		this.thumbnailPath = thumbnailPath;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

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
		return "Book [bookId=" + bookId + ", isbnId=" + isbnId + ", title=" + title + ", author=" + author
				+ ", publishedDate=" + publishedDate + ", description=" + description + ", pageCount=" + pageCount
				+ ", thumbnailPath=" + thumbnailPath + "]";
	}

}