package com.honcari.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.honcari.domain.GroupRelation;

@Repository
public class GroupRelationshipRepository {
	
	private static final RowMapper<GroupRelation> GROUP_R_ROW_MAPPER = (rs, i) -> {
		GroupRelation gr = new GroupRelation();
		gr.setId(rs.getInt("id"));
		gr.setUserId(rs.getInt("user_id"));
		gr.setGroupId(rs.getInt("group_id"));
		return gr;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	public GroupRelation findByUserIdAndGroupId(Integer userId, Integer groupId) {
		String sql = "SELECT id,user_id,group_id FROM group_relationship WHERE user_id = :userId AND group_id = :groupId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("groupId", groupId);
		List<GroupRelation> grList = template.query(sql, param, GROUP_R_ROW_MAPPER);	
		if(grList.isEmpty()) {
			return null;
		}
		return grList.get(0);
	}
	
	public void insert(Integer userId, Integer groupId) {
		String sql = "INSERT INTO group_relationship (user_id,group_id) VALUES (:userId,:groupId)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("groupId", groupId);
		template.update(sql, param);	
	}

}
