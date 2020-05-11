package com.honcari.domain;

/**
 * GoogleBooksAPIから取得したデータ用のドメイン.
 * 
 * @author hatakeyamakouta
 *
 */
public class SmallThumbnail {

	/**	サムネイルパス */
	private String smallThumbnail;

	public String getSmallThumbnail() {
		return smallThumbnail;
	}

	public void setSmallThumbnail(String smallThumbnail) {
		this.smallThumbnail = smallThumbnail;
	}

	@Override
	public String toString() {
		return "SmallThumbnail [smallThumbnail=" + smallThumbnail + "]";
	}
}
