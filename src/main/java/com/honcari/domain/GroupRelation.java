package com.honcari.domain;

/**
 * グループとユーザーを紐付けるドメイン.
 * 
 * @author yamadadai
 *
 */
public class GroupRelation {
	/** ID */
	private Integer id;
	/** ユーザーID */
	private Integer userId;
	/** グループID */
	private Integer GroupId;
	/** 参加状況 */
	private Integer relation_status;

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

	public Integer getRelation_status() {
		return relation_status;
	}

	public void setRelation_status(Integer relation_status) {
		this.relation_status = relation_status;
	}

	@Override
	public String toString() {
		return "GroupRelation [id=" + id + ", userId=" + userId + ", GroupId=" + GroupId + ", relation_status="
				+ relation_status + "]";
	}

	

}
