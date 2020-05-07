package com.honcari.form;

import javax.validation.constraints.NotBlank;

/**
 * GoogleBookApiを使用する際の検索フォーム.
 * 
 * @author hatakeyamakouta
 *
 */
public class SearchGoogleBookForm {

	/**	検索ワード */
	@NotBlank(message="検索ワード入力して下さい")
	private String searchWord;
	
	private Integer pageNumber;

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public String toString() {
		return "SearchGoogleBookForm [searchWord=" + searchWord + ", pageNumber=" + pageNumber + "]";
	}
}
