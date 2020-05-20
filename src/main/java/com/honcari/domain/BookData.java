package com.honcari.domain;

import java.util.List;

/**
 * GoogleBookAPIから取得したデータ用のドメイン.
 * 
 * @author hatakeyamakouta
 *
 */
public class BookData {
	
	/**	サムネイルパス */
	private SmallThumbnail imageLinks;
	
	/**	著者 */
	private List<String> authors;
	
	/** 著書名 */
	private String title;
	
	/**	出版日 */
	private String publishedDate;
	
	/**	isbnコード */
	private List<Identifier> industryIdentifiers;
	
	/**	書籍概要 */
	private String description;
	
	/**	ページ数 */
	private String pageCount;
	
	public SmallThumbnail getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(SmallThumbnail imageLinks) {
		this.imageLinks = imageLinks;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public List<Identifier> getIndustryIdentifiers() {
		return industryIdentifiers;
	}

	public void setIndustryIdentifiers(List<Identifier> industryIdentifiers) {
		this.industryIdentifiers = industryIdentifiers;
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

	@Override
	public String toString() {
		return "BookData [imageLinks=" + imageLinks + ", authors=" + authors + ", title=" + title + ", publishedDate="
				+ publishedDate + ", industryIdentifiers=" + industryIdentifiers + ", description=" + description
				+ ", pageCount=" + pageCount + "]";
	}
	
}
