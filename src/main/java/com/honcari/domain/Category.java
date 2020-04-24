package com.honcari.domain;

import java.util.List;

/**
 * カテゴリードメイン.
 * 
 * @author katsuya.fujishima
 *
 */
public class Category {
	/**	ID */
	private Integer categoryId;
	/**	名称 */
	private String name;
	/**	カテゴリー別ブックリスト */
	private List<BookOwner>bookOwnerList;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BookOwner> getBookOwnerList() {
		return bookOwnerList;
	}
	public void setBookOwnerList(List<BookOwner> bookOwnerList) {
		this.bookOwnerList = bookOwnerList;
	}
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name + ", bookOwnerList=" + bookOwnerList + "]";
	}
}
