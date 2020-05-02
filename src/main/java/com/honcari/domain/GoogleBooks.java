package com.honcari.domain;

import java.util.List;

/**
 * GoogleBookApi用の書籍ドメイン
 * 
 * @author hatakeyamakouta
 *
 */
public class GoogleBooks {
	/**	書籍数 */
	private String totalItems;
	/** 書籍情報 */
	private List<VolumeInfo> items;
	
	public String getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(String totalItems) {
		this.totalItems = totalItems;
	}
	public List<VolumeInfo> getItems() {
		return items;
	}
	public void setItems(List<VolumeInfo> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "GoogleBooks [totalItems=" + totalItems + ", items=" + items + "]";
	}
}
