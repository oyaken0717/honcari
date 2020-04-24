package com.honcari.domain;

/**
 * グループとユーザーを紐付けるドメイン.
 * 
 * @author yamadadai
 *
 */
public class GroupRelation {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * ユーザーID
	 */
	private Integer userId;
	/**
	 * グループID
	 */
	private Integer GroupId;
	
	/**
	 * グループ、ユーザー間の承認ステータス
	 */
	private Integer relationStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return GroupId;
	}

	public void setGroupId(Integer groupId) {
		GroupId = groupId;
	}

	public Integer getRelationStatus() {
		return relationStatus;
	}

	public void setRelationStatus(Integer relationStatus) {
		this.relationStatus = relationStatus;
	}

	@Override
	public String toString() {
		return "GroupRelation [id=" + id + ", userId=" + userId + ", GroupId=" + GroupId + ", relationStatus="
				+ relationStatus + "]";
	}

	

}
