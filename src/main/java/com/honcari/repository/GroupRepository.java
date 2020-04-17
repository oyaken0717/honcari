package com.honcari.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.honcari.domain.Group;
import com.honcari.domain.GroupRealationship;

/**
 * groupsテーブルを操作するリポジトリ.
 * 
 * @author yamadadai
 *
 */
@Repository
public class GroupRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcTemplate());
		SimpleJdbcInsert withtableName = simpleJdbcInsert.withTableName("groups");
		insert = withtableName.usingGeneratedKeyColumns("group_id");
	}
	
	public Group insertGroup(Group group) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(group);
		if(group.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			group.setId(key.intValue());
			return group;
		}
		return null;
	}
	
	public void insertGroupRelation(GroupRealationship realationship) {
		String sql = "INSERT INTO group_relationship(user_id, group_id) VALUES(:userId, :groupId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(realationship);
		template.update(sql, param);
	}
}
