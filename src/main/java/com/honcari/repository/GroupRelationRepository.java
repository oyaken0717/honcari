package com.honcari.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.honcari.domain.GroupRelation;
import com.honcari.domain.User;


/**
 *group_relationsテーブルを操作するリポジトリ.
 * 
 * @author yamaseki
 *
 */
@Repository
public class GroupRelationRepository {
	
	private static final RowMapper<GroupRelation> GROUP_R_ROW_MAPPER = (rs, i) -> {
		GroupRelation gr = new GroupRelation();
		gr.setId(rs.getInt("group_relation_id"));
		gr.setUserId(rs.getInt("user_id"));
		gr.setGroupId(rs.getInt("group_id"));
		gr.setRelation_status(rs.getInt("relation_status"));
		gr.setSendInviteUserId(rs.getInt("send_invite_user_id"));
		return gr;
	};
	
	private static final RowMapper<GroupRelation> GROUP_R_ROW_MAPPER2 = (rs, i) -> {
		GroupRelation gr = new GroupRelation();
		gr.setId(rs.getInt("gr_group_relation_id"));
		gr.setUserId(rs.getInt("gr_user_id"));
		gr.setGroupId(rs.getInt("gr_group_id"));
		gr.setRelation_status(rs.getInt("gr_relation_status"));
		gr.setSendInviteUserId(rs.getInt("gr_send_invite_user_id"));
		User user = new User();
		user.setUserId(rs.getInt("u_user_id"));
		user.setName(rs.getString("u_name"));
		user.setEmail(rs.getString("u_email"));
		user.setPassword(rs.getString("u_password"));
		user.setImagePath(rs.getString("u_image_path"));
		user.setProfile(rs.getString("u_profile"));
		user.setStatus(rs.getInt("u_status"));
		user.setUpdatePasswordDate(rs.getTimestamp("u_update_password_date"));
		gr.setUser(user);
		return gr;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	/**
	 * ユーザーidとグループidでgroup_relation情報を検索するメソッド.
	 * 
	 * @param userId ユーザーid
	 * @param groupId グループid
	 * @return group_relation情報
	 */
	public GroupRelation findByUserIdAndGroupId(Integer userId, Integer groupId) {
		String sql = "SELECT group_relation_id,user_id,group_id,relation_status,send_invite_user_id FROM group_relations WHERE user_id = :userId AND group_id = :groupId order by group_relation_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("groupId", groupId);
		List<GroupRelation> grList = template.query(sql, param, GROUP_R_ROW_MAPPER);	
		if(grList.isEmpty()) {
			return null;
		}
		return grList.get(0);
	}
	
	/**
	 * ユーザーidとグループidとステータスでgroup_relation情報を検索するメソッド.
	 * 
	 * @param userId ユーザーid
	 * @param groupId グループid
	 * @param status ステータス
	 * @return group_relation情報
	 */
	public GroupRelation findByUserIdAndGroupIdAndStatus(Integer userId, Integer groupId,Integer status) {
		String sql = "SELECT group_relation_id,user_id,group_id,relation_status,send_invite_user_id FROM group_relations WHERE user_id = :userId AND group_id = :groupId AND relation_status=:status order by group_relation_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("groupId", groupId).addValue("status", status);
		List<GroupRelation> grList = template.query(sql, param, GROUP_R_ROW_MAPPER);	
		if(grList.isEmpty()) {
			return null;
		}
		return grList.get(0);
	}
	
	/**
	 * グループIDとステータスと招待を送ったユーザーIDから検索するメソッド.
	 * 
	 * @param sendInviteUserId 招待を送ったユーザーid
	 * @param groupId グループid
	 * @param status ステータス
	 * @return group_relation情報
	 */
	public List<GroupRelation> findByGroupIdAndStatusAndSendInviteUserId(Integer groupId,Integer status,Integer sendInviteUserId) {
		String sql = "SELECT gr.group_relation_id gr_group_relation_id,gr.user_id gr_user_id,gr.group_id gr_group_id,gr.relation_status gr_relation_status,gr.send_invite_user_id gr_send_invite_user_id, "
				+ "u.user_id u_user_id,u.name u_name,u.email u_email,u.password u_password,u.image_path u_image_path,u.profile u_profile,u.status u_status,u.update_password_date u_update_password_date FROM group_relations gr "
				+ "LEFT OUTER JOIN users u ON gr.user_id = u.user_id WHERE gr.group_id = :groupId AND gr.relation_status = :status AND "
				+ "gr.send_invite_user_id = :sendInviteUserId order by gr.group_relation_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId).addValue("status", status).addValue("sendInviteUserId", sendInviteUserId);
		List<GroupRelation> grList = template.query(sql, param, GROUP_R_ROW_MAPPER2);	
		if(grList.isEmpty()) {
			return null;
		}
		return grList;
	}
	
	/**
	 * インサートメソッド.
	 * 
	 * @param userId ユーザーid
	 * @param groupId グループid
	 * @param status ステータス
	 */
	public void insert(Integer userId, Integer groupId,Integer status,Integer sendInviteUserId) {
		String sql = "INSERT INTO group_relations (user_id,group_id,relation_status,send_invite_user_id) VALUES (:userId,:groupId, :status, :sendInviteUserId)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("groupId", groupId).addValue("status", status).addValue("sendInviteUserId", sendInviteUserId);
		template.update(sql, param);	
	}
	
	/**
	 * アップデートメソッド.
	 * 
	 * @param gr group_relation情報
	 */
	public void update(GroupRelation gr) {
		String sql = "UPDATE group_relations SET group_relation_id=:id,user_id=:userId,group_id=:groupId,relation_status=:relation_status,send_invite_user_id = :sendInviteUserId WHERE group_relation_id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(gr);
		template.update(sql, param);	
	}
	
	/**
	 * デリートメソッド.
	 * 
	 * @param userId ユーザid
	 */
	public void delete(Integer userId) {
		String sql = "DELETE FROM group_relations WHERE user_id = :userId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		template.update(sql, param);
	}
	
	public Integer countByUserIdAndStatus(Integer userId,Integer status) {
		String sql = "SELECT COUNT(*) FROM group_relations WHERE user_id = :userId AND relation_status = :status";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		return template.queryForObject(sql, param,Integer.class);
	}

}
