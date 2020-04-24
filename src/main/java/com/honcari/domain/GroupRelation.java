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
<<<<<<< HEAD
	
	/**
	 * グループ、ユーザー間の承認ステータス
	 */
	private Integer relationStatus;
=======
	/** 参加状況 */
	private Integer relation_status;
>>>>>>> origin/develop

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

<<<<<<< HEAD
	public Integer getRelationStatus() {
		return relationStatus;
	}

	public void setRelationStatus(Integer relationStatus) {
		this.relationStatus = relationStatus;
=======
	public Integer getRelation_status() {
		return relation_status;
	}

	public void setRelation_status(Integer relation_status) {
		this.relation_status = relation_status;
>>>>>>> origin/develop
	}

	@Override
	public String toString() {
<<<<<<< HEAD
		return "GroupRelation [id=" + id + ", userId=" + userId + ", GroupId=" + GroupId + ", relationStatus="
				+ relationStatus + "]";
=======
		return "GroupRelation [id=" + id + ", userId=" + userId + ", GroupId=" + GroupId + ", relation_status="
				+ relation_status + "]";
>>>>>>> origin/develop
	}

	

}
