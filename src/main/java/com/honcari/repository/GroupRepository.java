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

import com.honcari.domain.BookOwner;
import com.honcari.domain.Group;
import com.honcari.domain.GroupRelation;
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
		List<BookOwner> bookOwnerList = new ArrayList<>();

		Group group = new Group();
		User user = new User();
		int beforeGroupId = 0;
		int beforeUserId = 0;
		int beforeBookOwnerId = 0;

		while (rs.next()) {
			int nowGroupId = rs.getInt("g_group_id");
			if (beforeGroupId != nowGroupId) {
				group = new Group();
				group.setId(nowGroupId);
				group.setName(rs.getString("g_name"));
				group.setDescription(rs.getString("g_description"));
				group.setOwnerUserId(rs.getInt("g_owner_user_id"));
				group.setIsPrivate(rs.getBoolean("g_is_private"));
				group.setDeleted(rs.getBoolean("g_deleted"));
				group.setUserList(userList);
				groupList.add(group);
				
				beforeGroupId = nowGroupId;
			}

			int nowUserId = rs.getInt("u_user_id");
			if (nowUserId != beforeUserId) {
				user = new User();
				bookOwnerList = new ArrayList<>();
				user.setId(nowUserId);
				user.setName(rs.getString("u_name"));
				user.setEmail(rs.getString("u_email"));
				user.setPassword(rs.getString("u_password"));
				user.setImagePath(rs.getString("u_image_path"));
				user.setProfile(rs.getString("u_profile"));
				user.setDeleted(rs.getBoolean("u_deleted"));
				user.setBookOwnerList(bookOwnerList);
				userList.add(user);

				beforeUserId = nowUserId;
			}

			int bookOwnerId = rs.getInt("bo_book_owner_id");
			if (bookOwnerId != beforeBookOwnerId) {
				BookOwner bookOwner = new BookOwner();
				bookOwner.setBookOwnerId(bookOwnerId);
				bookOwner.setUserId(rs.getInt("bo_user_id"));
				bookOwner.setBookId(rs.getInt("bo_book_id"));
				bookOwner.setCategoryId(rs.getInt("bo_category_id"));
				bookOwner.setBookStatus(rs.getInt("bo_book_status"));
				bookOwner.setComment(rs.getString("bo_comment"));
				bookOwnerList.add(bookOwner);

				beforeBookOwnerId = bookOwnerId;
			}

		}
		return groupList;
	};

	private static final RowMapper<Group> GROUP_ROW_MAPPER = (rs, i) -> {
		Group group = new Group();
		group.setId(rs.getInt("group_id"));
		group.setName(rs.getString("name"));
		group.setDescription(rs.getString("description"));
		group.setUserId(rs.getInt("user_id"));
		return group;
	};
	
	/**
	 * SQL文を取得するメソッド.
	 * 
	 * @return 定数化したSQL文
	 */
	private static final StringBuilder getSQL() {
		StringBuilder SQL = new StringBuilder();
		SQL.append("Select g.group_id g_group_id, g.name g_name, g.description g_description, g.owner_user_id g_owner_user_id, ");
		SQL.append("g.is_private g_is_private, g.deleted g_deleted, u.user_id u_user_id, u.name u_name, u.email u_email, ");
		SQL.append("u.password u_password, u.image_path u_image_path, u.profile u_profile, u.deleted u_deleted, ");
		SQL.append("bo.book_owner_id bo_book_owner_id, bo.user_id bo_user_id, bo.book_id bo_book_id, bo.category_id bo_category_id, ");
		SQL.append("bo.book_status bo_book_status, bo.comment bo_comment ");
		SQL.append("FROM groups g LEFT OUTER JOIN group_relations gr ON g.group_id = gr.group_id LEFT OUTER JOIN users u ");
		SQL.append("ON gr.user_id = u.user_id LEFT OUTER JOIN book_owners bo ON u.user_id = bo.user_id AND bo.book_status <> 0");
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
	 * グループとユーザーを紐づけた情報をDBに格納するメソッド.
	 * 
	 * @param realation グループとユーザーを紐付けた情報
	 */
	public void insertGroupRelation(GroupRelation realation) {
		String sql = "INSERT INTO group_relationship(user_id, group_id) VALUES(:userId, :groupId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(realation);
		template.update(sql, param);
	}

	/**
	 * 受け取ったパラメータからグループ情報を取得するメソッド.
	 * 
	 * @param name 検索パラメータ
	 * @return グループ情報リスト
	 */
	public List<Group> findByLikeName(String name) {
		String sql = "SELECT group_id,name,description,user_id FROM groups WHERE name LIKE :name ORDER BY group_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		return template.query(sql, param, GROUP_ROW_MAPPER);
	}
	
	/**
	 * グループIDからグループ情報を取得するメソッド.
	 * 
	 * @param groupId グループID
	 * @return グループ情報
	 */
	public Group findByGroupId(Integer groupId) {
		StringBuilder sql = getSQL();
		sql.append(" WHERE g.group_id = :groupId AND u.deleted = false ORDER BY g.group_id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		List<Group> groupList = template.query(sql.toString(), param, GROUP_RESULT_SET_EXTRACTOR);
		return groupList.get(0);
	}
}
