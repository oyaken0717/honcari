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

	@Override
	public String toString() {
		return "GroupRealationship [id=" + id + ", userId=" + userId + ", GroupId=" + GroupId + "]";
	}

}
