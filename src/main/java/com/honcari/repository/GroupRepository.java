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

import com.honcari.domain.Book;
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
				group.setRequestedOwnerUserId(rs.getInt("g_requested_owner_user_id"));
				group.setGroupImage(rs.getString("g_group_image"));
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
				beforeOwnedBookInfoId  = 0;
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
				User ownedBookInfoUser = new User();
				ownedBookInfoUser.setUserId(rs.getInt("obu_user_id"));
				ownedBookInfoUser.setName(rs.getString(("obu_name")));
				ownedBookInfoUser.setEmail(rs.getString("obu_email"));
				ownedBookInfoUser.setPassword(rs.getString("obu_password"));
				ownedBookInfoUser.setImagePath(rs.getString("obu_image_path"));
				ownedBookInfoUser.setProfile(rs.getString("obu_profile"));
				ownedBookInfoUser.setStatus(rs.getInt("obu_status"));
				ownedBookInfoUser.setUpdatePasswordDate(rs.getTimestamp("obu_update_password_date"));
				ownedBookInfo.setUser(ownedBookInfoUser);
				Book book = new Book();
				book.setBookId(rs.getInt("b_book_id"));
				book.setIsbnId(rs.getString("b_isbn_id"));
				book.setTitle(rs.getString("b_title"));
				book.setAuthor(rs.getString("b_author"));
				book.setPublishedDate(rs.getString("b_published_date"));
				book.setDescription(rs.getString("b_description"));
				book.setPageCount(rs.getString("b_page_count"));
				book.setThumbnailPath(rs.getString("b_thumbnail_path"));
				ownedBookInfo.setBook(book);
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
		group.setRequestedOwnerUserId(rs.getInt("requested_owner_user_id"));
		group.setGroupImage(rs.getString("group_image"));
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
	
	private static final RowMapper<Group> GROUP_ROW_MAPPER2 = (rs, i) -> {
		Group group = new Group();
		group.setId(rs.getInt("group_id"));
		group.setName(rs.getString("name"));
		group.setDescription(rs.getString("description"));
		group.setOwnerUserId(rs.getInt("owner_user_id"));
		group.setGroupStatus(rs.getInt("group_status"));
		group.setRequestedOwnerUserId(rs.getInt("requested_owner_user_id"));
		group.setGroupImage(rs.getString("group_image"));
		return group;
	};
	
	/**
	 * SQL文を取得するメソッド.
	 * 
	 * @return 定数化したSQL文
	 */
	private static final StringBuilder getSQL() {
		StringBuilder SQL = new StringBuilder();
		SQL.append("SELECT g.group_id g_group_id, g.name g_name, g.description g_description, g.owner_user_id g_owner_user_id, g.requested_owner_user_id g_requested_owner_user_id, g.group_image g_group_image,");
		SQL.append("g.group_status g_group_status,ou.user_id ou_user_id,ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path,ou.profile ou_profile, ou.status ou_status,");
		SQL.append("u.user_id u_user_id, u.name u_name, u.email u_email,u.password u_password, u.image_path u_image_path, u.profile u_profile, u.status u_status, ");
		SQL.append("o.owned_book_info_id o_owned_book_info_id, o.user_id o_user_id, o.book_id o_book_id, o.category_id o_category_id, ");
		SQL.append("o.book_status o_book_status, o.comment o_comment, obu.user_id obu_user_id, obu.name obu_name, obu.email obu_email, obu.password obu_password, obu.image_path obu_image_path, obu.profile obu_profile,");
		SQL.append("obu.status obu_status, obu.update_password_date obu_update_password_date,b.book_id b_book_id, b.isbn_id b_isbn_id, b.title b_title, b.author b_author, b.published_date b_published_date, b.description b_description,");
		SQL.append("b.page_count b_page_count, b.thumbnail_path b_thumbnail_path FROM groups g LEFT OUTER JOIN group_relations gr ");
		SQL.append("ON g.group_id = gr.group_id LEFT OUTER JOIN users ou ON g.owner_user_id = ou.user_id LEFT OUTER JOIN users u ON gr.user_id = u.user_id LEFT OUTER JOIN owned_book_info o ");
		SQL.append("ON u.user_id = o.user_id LEFT OUTER JOIN users obu ON o.user_id = obu.user_id LEFT OUTER JOIN books b ON o.book_id = b.book_id ");//AND o.book_status <> 1
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
	public List<Group> findByLikeName(String name,Integer offset) {
		String sql = "SELECT g.group_id,g.name,g.description,g.owner_user_id,g.group_status,g.requested_owner_user_id,g.group_image, ou.user_id ou_user_id,"
				+ "ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path, "
				+ "ou.profile ou_profile, ou.status ou_status FROM groups g LEFT OUTER JOIN users ou ON "
				+ "g.owner_user_id = ou.user_id WHERE g.name LIKE :name AND g.group_status = 0 ORDER BY g.group_id OFFSET :offset LIMIT 9";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("offset", offset);
		List<Group> groupList = template.query(sql.toString(), param, GROUP_ROW_MAPPER);
		return groupList;
	}
	
//	/**
//	 * 受け取ったパラメータからグループ情報を取得するメソッド.
//	 * 
//	 * @param name 検索パラメータ
//	 * @return グループ情報リスト
//	 */
//	public List<Group> findByLikeName2(String name,Integer offset) {
//		StringBuilder sql = getSQL();
//		sql.append(" WHERE g.name LIKE :name ORDER BY g.group_id");
//		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
//		List<Group> groupList = template.query(sql.toString(), param, GROUP_RESULT_SET_EXTRACTOR);
//		return groupList;
//	}
	
	/**
	 * グループIDからグループ情報を取得するメソッド.
	 * 
	 * @param groupId グループID
	 * @return グループ情報
	 */
	public Group findByGroupId(Integer groupId) {
		StringBuilder sql = getSQL();
		sql.append("WHERE g.group_id = :groupId AND u.status <> 1 ORDER BY g.group_id , u.user_id");
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
		sql.append("WHERE g.group_id = :groupId AND gr.relation_status = 1 AND u.status <> 1 ORDER BY g.group_id , u.user_id");
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
		String sql = "SELECT g.group_id,g.name,g.description,g.owner_user_id,g.group_status, g.requested_owner_user_id,g.group_image,ou.user_id ou_user_id,"
				+ "ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path, "
				+ "ou.profile ou_profile, ou.status ou_status FROM groups g LEFT OUTER JOIN users ou ON "
				+ "g.owner_user_id = ou.user_id WHERE owner_user_id = :ownerUserId order by group_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("ownerUserId",ownerUserId);
		List<Group> groupList = template.query(sql, param, GROUP_ROW_MAPPER);
		if(groupList.isEmpty())return null;
		return groupList;
	}
	
	public List<Group> findByOwnerUserIdAndStatus(Integer ownerUserId,Integer status) {
		String sql = "SELECT g.group_id,g.name,g.description,g.owner_user_id,g.group_status, g.requested_owner_user_id,g.group_image, ou.user_id ou_user_id,"
				+ "ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path, "
				+ "ou.profile ou_profile, ou.status ou_status FROM groups g LEFT OUTER JOIN users ou ON "
				+ "g.owner_user_id = ou.user_id WHERE owner_user_id = :ownerUserId AND g.group_status = status order by group_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("ownerUserId",ownerUserId).addValue("status", status);
		List<Group> groupList = template.query(sql, param, GROUP_ROW_MAPPER);
		if(groupList.isEmpty())return null;
		return groupList;
	}
	
	/**
	 * 受け取ったパラメータからグループ情報を取得するメソッド.
	 * 
	 * @param ownerUserId 検索パラメータ
	 * @return グループ情報リスト
	 */
	public List<Group> findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "SELECT g.group_id,g.name,g.description,g.owner_user_id,g.group_status,g.requested_owner_user_id,g.group_image, ou.user_id ou_user_id,"
				+ "ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path, "
				+ "ou.profile ou_profile, ou.status ou_status FROM groups g LEFT OUTER JOIN users ou ON "
				+ "g.owner_user_id = ou.user_id LEFT OUTER JOIN group_relations gr ON g.group_id = gr.group_id WHERE gr.user_id = :userId AND gr.relation_status = :status ORDER BY g.group_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId).addValue("status", status);
		List<Group> groupList = template.query(sql, param, GROUP_ROW_MAPPER);
		if(groupList.isEmpty())return null;
		return groupList;
	}
	
	/**
	 * 受け取ったパラメータからグループ情報を取得するメソッド.
	 * 
	 * @param ownerUserId 検索パラメータ
	 * @return グループ情報リスト
	 */
	public List<Group> findByUserIdAndGroupStatus(Integer userId,Integer groupStatus, Integer relationStatus) {
		String sql = "SELECT g.group_id,g.name,g.description,g.owner_user_id,g.group_status,g.requested_owner_user_id, g.group_image,ou.user_id ou_user_id,"
				+ "ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path, "
				+ "ou.profile ou_profile, ou.status ou_status FROM groups g LEFT OUTER JOIN users ou ON "
				+ "g.owner_user_id = ou.user_id LEFT OUTER JOIN group_relations gr ON g.group_id = gr.group_id WHERE gr.user_id = :userId AND g.group_status = :groupStatus AND gr.relation_status = :relationStatus ORDER BY g.group_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId).addValue("groupStatus", groupStatus).addValue("relationStatus", relationStatus);
		List<Group> groupList = template.query(sql, param, GROUP_ROW_MAPPER);
		if(groupList.isEmpty())return null;
		return groupList;
	}
	
	public List<Group> findByRequestedOwnerUserId(Integer userId) {
		String sql = "SELECT group_id,name,description,owner_user_id,group_status,requested_owner_user_id,group_image FROM groups WHERE requested_owner_user_id = :userId ORDER BY group_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);
		List<Group> groupList = template.query(sql, param, GROUP_ROW_MAPPER2);
		if(groupList.isEmpty())return null;
		return groupList;
	}
	
	public void update(Group group) {
		String sql = "UPDATE groups SET name=:name,description=:description,owner_user_id=:ownerUserId,group_status=:groupStatus,requested_owner_user_id = :requestedOwnerUserId, group_image=:groupImage WHERE group_id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(group);
		template.update(sql, param);
	}
	
	public void delete(Integer id) {
		String sql = "DELETE FROM groups WHERE group_id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		template.update(sql, param);
	}
	
	public Integer count(String name) {
		String sql = "SELECT COUNT(*) FROM groups WHERE name LIKE :name AND group_status = 0";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		return template.queryForObject(sql, param,Integer.class);
	}
	
	public Integer countOwnerRequest(Integer userId) {
		String sql = "SELECT COUNT(*) FROM groups WHERE requested_owner_user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);
		return template.queryForObject(sql, param,Integer.class);
	}
	
	/**
	 * ユーザidからグループ一覧を取得するメソッド.
	 * 
	 * @param userId ユーザid
	 * @return ユーザidに一致したグループ一覧
	 */
	public List<Group> findByUserId(Integer userId){
		String sql = "SELECT g.group_id,g.name,g.description,g.owner_user_id,g.group_status,g.requested_owner_user_id, g.group_image,ou.user_id ou_user_id,"
				+ "ou.name ou_name, ou.email ou_email,ou.password ou_password, ou.image_path ou_image_path, "
				+ "ou.profile ou_profile, ou.status ou_status FROM groups g LEFT OUTER JOIN users ou ON "
				+ "g.owner_user_id = ou.user_id WHERE g.owner_user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		return template.query(sql, param, GROUP_ROW_MAPPER);
	}
}
