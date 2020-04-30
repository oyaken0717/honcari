package com.honcari.domain;

import java.util.Date;

import com.honcari.common.RentalStatusEnum;

public class BookRental {

	/** ID */
	private Integer bookRentalId;
	/** 所有情報ID */
	private Integer ownedBookInfoId;
	/** 借り手ユーザーID */
	private Integer borrowUserId;
	/** 貸し借り状況 */
	private Integer rentalStatus;
	/** 貸出期限 */
	private Date deadline;
	/** 本所有情報 */
	private OwnedBookInfo ownedBookInfo;
	/** 借り手ユーザー */
	private User borrowUser;
	/** 初回登録者 */
	private String creationUserName;
	/** （最終）更新者 */
	private String updateUserName;
	/** バージョン（楽観ロック用） */
	private Integer version;

	public BookRental() {
	}

	public BookRental(Integer bookRentalId, Integer ownedBookInfoId, Integer borrowUserId, Integer rentalStatus,
			Date deadline, OwnedBookInfo ownedBookInfo, User borrowUser, String creationUserName, String updateUserName,
			Integer version) {
		super();
		this.bookRentalId = bookRentalId;
		this.ownedBookInfoId = ownedBookInfoId;
		this.borrowUserId = borrowUserId;
		this.rentalStatus = rentalStatus;
		this.deadline = deadline;
		this.ownedBookInfo = ownedBookInfo;
		this.borrowUser = borrowUser;
		this.creationUserName = creationUserName;
		this.updateUserName = updateUserName;
		this.version = version;
	}

	/**
	 * 数字から貸出状況を取得する.
	 * 
	 * @return 貸出状況
	 */
	public String getBookRentalStatus() {
		return RentalStatusEnum.of(rentalStatus).getKey();
	}

	public Integer getBookRentalId() {
		return bookRentalId;
	}

	public void setBookRentalId(Integer bookRentalId) {
		this.bookRentalId = bookRentalId;
	}

	public Integer getOwnedBookInfoId() {
		return ownedBookInfoId;
	}

	public void setOwnedBookInfoId(Integer ownedBookInfoId) {
		this.ownedBookInfoId = ownedBookInfoId;
	}

	public Integer getBorrowUserId() {
		return borrowUserId;
	}

	public void setBorrowUserId(Integer borrowUserId) {
		this.borrowUserId = borrowUserId;
	}

	public Integer getRentalStatus() {
		return rentalStatus;
	}

	public void setRentalStatus(Integer rentalStatus) {
		this.rentalStatus = rentalStatus;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public OwnedBookInfo getOwnedBookInfo() {
		return ownedBookInfo;
	}

	public void setOwnedBookInfo(OwnedBookInfo ownedBookInfo) {
		this.ownedBookInfo = ownedBookInfo;
	}

	public User getBorrowUser() {
		return borrowUser;
	}

	public void setBorrowUser(User borrowUser) {
		this.borrowUser = borrowUser;
	}

	public String getCreationUserName() {
		return creationUserName;
	}

	public void setCreationUserName(String creationUserName) {
		this.creationUserName = creationUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "BookRental [bookRentalId=" + bookRentalId + ", ownedBookInfoId=" + ownedBookInfoId + ", borrowUserId="
				+ borrowUserId + ", rentalStatus=" + rentalStatus + ", deadline=" + deadline + ", ownedBookInfo="
				+ ownedBookInfo + ", borrowUser=" + borrowUser + ", creationUserName=" + creationUserName
				+ ", updateUserName=" + updateUserName + ", version=" + version + "]";
	}

}
