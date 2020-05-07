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
		String sql = "SELECT group_relation_id,user_id,group_id,relation_status FROM group_relations WHERE user_id = :userId AND group_id = :groupId";
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
		String sql = "SELECT group_relation_id,user_id,group_id,relation_status FROM group_relations WHERE user_id = :userId AND group_id = :groupId AND relation_status=:status";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("groupId", groupId).addValue("status", status);
		List<GroupRelation> grList = template.query(sql, param, GROUP_R_ROW_MAPPER);	
		if(grList.isEmpty()) {
			return null;
		}
		return grList.get(0);
	}
	
	/**
	 * インサートメソッド.
	 * 
	 * @param userId ユーザーid
	 * @param groupId グループid
	 * @param status ステータス
	 */
	public void insert(Integer userId, Integer groupId,Integer status) {
		String sql = "INSERT INTO group_relations (user_id,group_id,relation_status) VALUES (:userId,:groupId, :status)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("groupId", groupId).addValue("status", status);
		template.update(sql, param);	
	}
	
	/**
	 * アップデートメソッド.
	 * 
	 * @param gr group_relation情報
	 */
	public void update(GroupRelation gr) {
		String sql = "UPDATE group_relations SET group_relation_id=:id,user_id=:userId,group_id=:groupId,relation_status=:relation_status WHERE group_relation_id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(gr);
		template.update(sql, param);	
	}

}
