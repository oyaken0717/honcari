package com.honcari.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.honcari.domain.GroupRelation;

//yamaseki
@Repository
public class GroupRelationRepository {
	
	private static final RowMapper<GroupRelation> GROUP_R_ROW_MAPPER = (rs, i) -> {
		GroupRelation gr = new GroupRelation();
		gr.setId(rs.getInt("id"));
		gr.setUserId(rs.getInt("user_id"));
		gr.setGroupId(rs.getInt("group_id"));
		gr.setRelation_status(rs.getInt("relation_status"));
		return gr;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	public GroupRelation findByUserIdAndGroupId(Integer userId, Integer groupId) {
		String sql = "SELECT id,user_id,group_id,relation_status FROM group_relations WHERE user_id = :userId AND group_id = :groupId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("groupId", groupId);
		List<GroupRelation> grList = template.query(sql, param, GROUP_R_ROW_MAPPER);	
		if(grList.isEmpty()) {
			return null;
		}
		return grList.get(0);
	}
	
	public void insert(Integer userId, Integer groupId) {
		String sql = "INSERT INTO group_relations (user_id,group_id,relation_status) VALUES (:userId,:groupId, 0)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("groupId", groupId);
		template.update(sql, param);	
	}

}
