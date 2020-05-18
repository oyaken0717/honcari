package com.honcari.domain;

import java.sql.Timestamp;
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
	/** 申請貸出開始日 */
	private Date requestBeginning;
	/** 貸出開始日 */
	private Date beginning;
	/** 申請貸出期限 */
	private Date requestDeadline;
	/** 貸出期限 */
	private Date deadline;
	/** 承認日時 */
	private Timestamp approvalDate;
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
			Date requestBeginning, Date beginning, Date requestDeadline, Date deadline, Timestamp approvalDate,
			OwnedBookInfo ownedBookInfo, User borrowUser, String creationUserName, String updateUserName,
			Integer version) {
		super();
		this.bookRentalId = bookRentalId;
		this.ownedBookInfoId = ownedBookInfoId;
		this.borrowUserId = borrowUserId;
		this.rentalStatus = rentalStatus;
		this.requestBeginning = requestBeginning;
		this.beginning = beginning;
		this.requestDeadline = requestDeadline;
		this.deadline = deadline;
		this.approvalDate = approvalDate;
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

	public Date getRequestBeginning() {
		return requestBeginning;
	}

	public void setRequestBeginning(Date requestBeginning) {
		this.requestBeginning = requestBeginning;
	}

	public Date getBeginning() {
		return beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

	public Date getRequestDeadline() {
		return requestDeadline;
	}

	public void setRequestDeadline(Date requestDeadline) {
		this.requestDeadline = requestDeadline;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Timestamp getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Timestamp approvalDate) {
		this.approvalDate = approvalDate;
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
				+ borrowUserId + ", rentalStatus=" + rentalStatus + ", requestBeginning=" + requestBeginning
				+ ", beginning=" + beginning + ", requestDeadline=" + requestDeadline + ", deadline=" + deadline
				+ ", approvalDate=" + approvalDate + ", ownedBookInfo=" + ownedBookInfo + ", borrowUser=" + borrowUser
				+ ", creationUserName=" + creationUserName + ", updateUserName=" + updateUserName + ", version="
				+ version + "]";
	}

}
