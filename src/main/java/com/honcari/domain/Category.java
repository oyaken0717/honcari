package com.honcari.domain;

import java.util.List;

public class Category {
	private Integer id;
	private String name;
	private List<Book> bookList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Book> getBookList() {
		return bookList;
	}
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", bookList=" + bookList + "]";
	}
	
	

}
