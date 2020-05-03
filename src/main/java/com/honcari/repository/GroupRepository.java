package com.honcari.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.honcari.domain.Group;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.domain.User;

/**
 * groupsテーブルを操作するリポジトリ.
 * 
 * @author yamadadai
 *
 */
@Repository
public class GroupRepository {

	private static final ResultSetExtractor<List<Group>> GROUP_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Group> groupList = new ArrayList<>();
		List<User> userList = new ArrayList<>();
		List<OwnedBookInfo> ownedBookInfoList = new ArrayList<>();

		Group group = new Group();
		User ownerUser = null;
		User user = new User();
		int beforeGroupId = 0;
		int beforeUserId = 0;
		int beforeOwnedBookInfoId = 0;

		while (rs.next()) {
			int nowGroupId = rs.getInt("g_group_id");
			if (beforeGroupId != nowGroupId) {
				group = new Group();
				group.setId(nowGroupId);
				group.setName(rs.getString("g_name"));
				group.setDescription(rs.getString("g_description"));
				group.setOwnerUserId(rs.getInt("g_owner_user_id"));
				group.setGroupStatus(rs.getInt("g_group_status"));
				group.setUserList(userList);
				group.setOwnerUser(ownerUser);
				ownerUser =  new User();
				ownerUser.setUserId(rs.getInt("ou_user_id"));
				ownerUser.setName(rs.getString("ou_name"));
				ownerUser.setEmail(rs.getString("ou_email"));
				ownerUser.setPassword(rs.getString("ou_password"));
				ownerUser.setImagePath(rs.getString("ou_image_path"));
				ownerUser.setProfile(rs.getString("ou_profile"));
				ownerUser.setStatus(rs.getInt("ou_status"));
				group.setOwnerUser(ownerUser);
				groupList.add(group);
				
				beforeGroupId = nowGroupId;
			}

			int nowUserId = rs.getInt("u_user_id");
			if (nowUserId != beforeUserId) {
				user = new User();
				ownedBookInfoList = new ArrayList<>();
				user.setUserId(nowUserId);
				user.setName(rs.getString("u_name"));
				user.setEmail(rs.getString("u_email"));
				user.setPassword(rs.getString("u_password"));
				user.setImagePath(rs.getString("u_image_path"));
				user.setProfile(rs.getString("u_profile"));
				user.setStatus(rs.getInt("u_status"));
				user.setOwnedBookInfoList(ownedBookInfoList);
				userList.add(user);

				beforeUserId = nowUserId;
			}

			int OwnedBookInfoId = rs.getInt("o_owned_book_info_id");
			if (OwnedBookInfoId != beforeOwnedBookInfoId) {
				OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
				ownedBookInfo.setOwnedBookInfoId(OwnedBookInfoId);
				ownedBookInfo.setUserId(rs.getInt("o_user_id"));
				ownedBookInfo.setBookId(rs.getInt("o_book_id"));
				ownedBookInfo.setCategoryId(rs.getInt("o_category_id"));
				ownedBookInfo.setBookStatus(rs.getInt("o_book_status"));
				ownedBookInfo.setComment(rs.getString("o_comment"));
				ownedBookInfoList.add(ownedBookInfo);

				beforeOwnedBookInfoId = OwnedBookInfoId;
			}

		}
		return groupList;
	};

	private static final RowMapper<Group> GROUP_ROW_MAPPER = (rs, i) -> {
		Group group = new Group();
		group.setId(rs.getInt("group_id"));
		group.setName(rs.getString("name"));
		group.setDescription(rs.getString("description"));
		group.setOwnerUserId(rs.getInt("owner_user_id"));
		group.setGroupStatus(rs.getInt("group_status"));
		User ownerUser =  new User();
		ownerUser.setUserId(rs.getInt("ou_user_id"));
		ownerUser.setName(rs.getString("ou_name"));
		ownerUser.setEmail(rs.getString("ou_email"));
		ownerUser.setPassword(rs.getString("ou_password"));
		ownerUser.setImagePath(rs.getString("ou_image_path"));
		ownerUser.setProfile(rs.getString("ou_profile"));
		ownerUser.setStatus(rs.getInt("ou_status"));
		group.setOwnerUser(ownerUser);
		return group;
	};
	
	/**
	 * SQL文を取得するメソッド.
	 * 
	 * @return 定数化したSQL文
	 */
	private static final StringBuilder getSQL() {
		StringBuilder SQL = new StringBuilder();
		SQL.append("SELECT g.group_id g_group_id, g.name g_name, g.description g_description, g.owner_user_id g_owner_user_id, ");
		SQL.append("g.group_status g_group_status,ou.user_id ou_user_id,ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path,ou.profile ou_profile, ou.status ou_status,");
		SQL.append("u.user_id u_user_id, u.name u_name, u.email u_email,u.password u_password, u.image_path u_image_path, u.profile u_profile, u.status u_status, ");
		SQL.append("o.owned_book_info_id o_owned_book_info_id, o.user_id o_user_id, o.book_id o_book_id, o.category_id o_category_id, ");
		SQL.append("o.book_status o_book_status, o.comment o_comment FROM groups g LEFT OUTER JOIN group_relations gr ");
		SQL.append("ON g.group_id = gr.group_id LEFT OUTER JOIN users ou ON g.owner_user_id = ou.user_id LEFT OUTER JOIN users u ON gr.user_id = u.user_id LEFT OUTER JOIN owned_book_info o ");
		SQL.append("ON u.user_id = o.user_id AND o.book_status <> 1 ");
		return SQL;
	}
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcTemplate());
		SimpleJdbcInsert withtableName = simpleJdbcInsert.withTableName("groups");
		insert = withtableName.usingGeneratedKeyColumns("group_id");
	}

	/**
	 * グループ情報をDBに格納するメソッド.
	 * 
	 * @param group グループ情報
	 * @return グループ情報
	 */
	public Group insertGroup(Group group) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(group);
		if (group.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			group.setId(key.intValue());
			return group;
		}
		return null;
	}

	/**
	 * 受け取ったパラメータからグループ情報を取得するメソッド.
	 * 
	 * @param name 検索パラメータ
	 * @return グループ情報リスト
	 */
	public List<Group> findByLikeName(String name) {
//		String sql = "SELECT g.group_id,g.name,g.description,g.owner_user_id,g.group_status, ou.user_id ou_user_id,"
//				+ "ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path, "
//				+ "ou.profile ou_profile, ou.status ou_status FROM groups g LEFT OUTER JOIN users ou ON "
//				+ "g.owner_user_id = ou.user_id WHERE g.name LIKE :name ORDER BY g.group_id";
		StringBuilder sql = getSQL();
		sql.append(" WHERE g.name LIKE :name ORDER BY g.group_id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		List<Group> groupList = template.query(sql.toString(), param, GROUP_RESULT_SET_EXTRACTOR);
		return groupList;
	}
	
	/**
	 * グループIDからグループ情報を取得するメソッド.
	 * 
	 * @param groupId グループID
	 * @return グループ情報
	 */
	public Group findByGroupId(Integer groupId) {
		StringBuilder sql = getSQL();
		sql.append("WHERE g.group_id = :groupId AND u.status <> 1 ORDER BY g.group_id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		List<Group> groupList = template.query(sql.toString(), param, GROUP_RESULT_SET_EXTRACTOR);
		return groupList.get(0);
	}
	
	/**
	 * グループIDからグループ情報を取得するメソッド.
	 * 
	 * @param groupId グループID
	 * @return グループ情報
	 */
	public Group findByGroupIdAndRelationStatus(Integer groupId) {
		StringBuilder sql = getSQL();
		sql.append("WHERE g.group_id = :groupId AND gr.relation_status = 1 AND u.status <> 1 ORDER BY g.group_id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		List<Group> groupList = template.query(sql.toString(), param, GROUP_RESULT_SET_EXTRACTOR);
		return groupList.get(0);
	}
	
	/**
	 * 受け取ったパラメータからグループ情報を取得するメソッド.
	 * 
	 * @param ownerUserId 検索パラメータ
	 * @return グループ情報リスト
	 */
	public List<Group> findByOwnerUserId(Integer ownerUserId) {
		String sql = "SELECT g.group_id,g.name,g.description,g.owner_user_id,g.group_status, ou.user_id ou_user_id,"
				+ "ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path, "
				+ "ou.profile ou_profile, ou.status ou_status FROM groups g LEFT OUTER JOIN users ou ON "
				+ "g.owner_user_id = ou.user_id WHERE owner_user_id = :ownerUserId order by group_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("ownerUserId",ownerUserId);
		List<Group> groupList = template.query(sql, param, GROUP_ROW_MAPPER);
		if(groupList.isEmpty())return null;
		return groupList;
	}
	
	public void update(Group group) {
		String sql = "UPDATE groups SET name=:name,description=:description,owner_user_id=:ownerUserId,group_status=:groupStatus WHERE group_id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(group);
		template.update(sql, param);
	}
	
	public void delete(Integer id) {
		String sql = "DELETE FROM groups WHERE group_id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		template.update(sql, param);
	}
}
