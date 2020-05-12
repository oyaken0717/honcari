package com.honcari.form;

public class EditOwnedBookInfoForm {

	/**	本の所有者情報に関するid */
	private Integer ownedBookInfoId;
	/** カテゴリid */
	private Integer categoryId;
	/**	コメント */
	private String comment;
	/**	書籍状況 */
	private Integer bookStatus;
	/**	バージョン */
	private Integer version;
	public Integer getOwnedBookInfoId() {
		return ownedBookInfoId;
	}
	public void setOwnedBookInfoId(Integer ownedBookInfoId) {
		this.ownedBookInfoId = ownedBookInfoId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "EditOwnedBookInfoForm [ownedBookInfoId=" + ownedBookInfoId + ", categoryId=" + categoryId + ", comment="
				+ comment + ", bookStatus=" + bookStatus + ", version=" + version + "]";
	}
	
}