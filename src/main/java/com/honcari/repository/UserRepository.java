package com.honcari.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.honcari.domain.Book;
import com.honcari.domain.Group;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.domain.User;

/**
 * usersテーブルを操作するリポジトリ.
 * 
 * @author katsuya.fujishima
 *
 */
@Repository
public class UserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setUserId(rs.getInt("user_id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setImagePath(rs.getString("image_path"));
		user.setProfile(rs.getString("profile"));
		user.setStatus(rs.getInt("status"));
		user.setUpdatePasswordDate(rs.getTimestamp("update_password_date"));
		
		return user;
	};

	private static final ResultSetExtractor<List<User>> USER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<User> userList = new ArrayList<>();
		List<OwnedBookInfo> ownedBookInfoList = new ArrayList<>();
		List<Group> groupList = new ArrayList<>();
		List<Integer> bookIdList = null;
		int beforeUserId = 0;
		int beforeGroupId = 0;

		while (rs.next()) {
			int nowUserId = rs.getInt("u_user_id");
			if (nowUserId != beforeUserId) {
				User user = new User();
				ownedBookInfoList = new ArrayList<>();
				user.setUserId(nowUserId);
				user.setName(rs.getString("u_name"));
				user.setEmail(rs.getString("u_email"));
				user.setPassword(rs.getString("u_password"));
				user.setImagePath(rs.getString("u_image_path"));
				user.setProfile(rs.getString("u_profile"));
				user.setStatus(rs.getInt("u_status"));
				user.setUpdatePasswordDate(rs.getTimestamp("u_update_password_date"));
				user.setOwnedBookInfoList(ownedBookInfoList);
				user.setGroupList(groupList);
				userList.add(user);

				beforeUserId = nowUserId;
				bookIdList = new ArrayList<>();
			}

			int groupId = rs.getInt("g_group_id");
			if (groupId != beforeGroupId) {
				Group group = new Group();
				group.setId(rs.getInt("g_group_id"));
				group.setName(rs.getString("g_name"));
				group.setDescription(rs.getString("g_description"));
				group.setOwnerUserId(rs.getInt("g_owner_user_id"));
				groupList.add(group);

				beforeGroupId = groupId;
			}
			
			int bookId = rs.getInt("b_book_id");
			if (!bookIdList.contains(bookId)) {
				OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
				ownedBookInfo.setComment(rs.getString("o_comment"));
				ownedBookInfo.setBookStatus(rs.getInt("o_book_status"));
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
				
				bookIdList.add(bookId);
			}
		}
		return userList;
	};

	/** usersテーブルのみから検索する際に利用するSQL文 */
	private static final String BASE_SQL_FROM_USERS = "SELECT user_id,name,email,password,image_path,profile,status,update_password_date from Users ";
	
	/** 5テーブルから検索する際に利用するSQL文 */
	private static final String BASE_SQL_FROM_5 = "SELECT u.user_id u_user_id,u.name u_name,u.email u_email,u.password u_password,u.image_path u_image_path,u.profile u_profile,u.status u_status,u.update_password_date u_update_password_date,"
			+ "b.book_id b_book_id,b.isbn_id b_isbn_id, b.title b_title, b.author b_author,"
			+ "b.published_date b_published_date, b.description b_description, b.page_count b_page_count, b.thumbnail_path b_thumbnail_path,"
			+ "o.comment o_comment, o.book_status o_book_status, o.category_id o_category_id, g.group_id g_group_id,g.name g_name,g.description g_description,g.owner_user_id g_owner_user_id "
			+ "FROM users u	LEFT JOIN owned_book_info o ON u.user_id = o.user_id AND o.book_status != 9 "
			+ "LEFT JOIN books b ON o.book_id = b.book_id LEFT JOIN group_relations gr ON u.user_id = gr.user_id "
			+ "LEFT JOIN groups g ON g.group_id=gr.group_id AND g.group_status != 9 ";

	/**
	 * ユーザー情報を挿入するメソッド.
	 * 
	 * @param user ユーザー
	 */
	public void insert(User user) {
		String sql = "INSERT INTO Users(name,email,password,image_path,update_password_date)VALUES(:name,:email,:password,:imagePath,:updatePasswordDate)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}

	/**
	 * メールアドレスから検索するメソッド.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User findByEmail(String email) {
		String sql = BASE_SQL_FROM_USERS + "where email = :email AND status != 9;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * ユーザーIDから検索するメソッド
	 * 
	 * @param userId ユーザーID
	 * @return ユーザー情報リスト
	 */
	public User findByUserId(Integer userId) {
		String sql = BASE_SQL_FROM_5 + "WHERE u.user_id = :userId AND u.status != 9 order by g.group_id, b.book_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<User> userList = template.query(sql, param, USER_RESULT_SET_EXTRACTOR);
		return userList.get(0);

	}
	
	public User findByUserIdAndRelationStatus(Integer userId,Integer status) {
		String sql = BASE_SQL_FROM_5 + "WHERE u.user_id = :userId AND gr.relation_status=:status AND u.status != 9 order by g.group_id, b.book_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<User> userList = template.query(sql, param, USER_RESULT_SET_EXTRACTOR);
		if(userList.isEmpty())return null;
		return userList.get(0);

	}

	/**
	 * 名前から検索するメソッド.
	 * 
	 * @param name 検索名
	 * @return ユーザー情報
	 */
	public User findByName(String name) {
		String sql = BASE_SQL_FROM_USERS + "WHERE name=:name AND status != 9;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if(userList.isEmpty())return null;
		return userList.get(0);
	}
	
	/**
	 * 名前から退会したユーザーを含めた全ユーザーから検索するメソッド.
	 * 
	 * @param name 検索名
	 * @return ユーザー情報
	 */
	public User findAnyUserByName(String name) {
		String sql = BASE_SQL_FROM_USERS + "WHERE name=:name;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if(userList.isEmpty()) {
			return null;
		}
		return userList.get(0);
	}
	
	/**
	 * 名前からあいまい検索するメソッド.
	 * 
	 * @param name 検索名
	 * @return ユーザー情報
	 */
	public List<User> findByNameLike(String name, Integer userId) {
		String sql = BASE_SQL_FROM_USERS + "WHERE name LIKE :name AND status != 9 AND user_id != :userId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("userId", userId);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.isEmpty()) {
			return null;
		}
		return userList;
	}
	
	/**
	 * グループ作成者のid、グループidから、グループに所属していないユーザーをあいまい検索
	 * 注意！グループ招待機能のみで使ってます。
	 * @param name 検索名
	 * @return ユーザー情報
	 */
	public List<User> findByNameLikeAndGroupIdForInvite(String name, Integer userId,Integer groupId) {
		String sql = BASE_SQL_FROM_USERS + "WHERE name LIKE :name AND status != 9 AND user_id <> :userId AND "
				+ "user_id NOT IN (SELECT user_id FROM group_relations WHERE group_id = :groupId)"
				+ "OR user_id IN (SELECT user_id FROM group_relations WHERE group_id = :groupId and relation_status = 9 )";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("userId", userId).addValue("groupId", groupId);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.isEmpty()) {
			return null;
		}
		return userList;
	}
	
	/**
	 * グループ作成者のid、グループidから、グループに所属していないユーザーをあいまい検索
	 * 注意！オーナー権限委任機能のみで使ってます。
	 * @param name 検索名
	 * @return ユーザー情報
	 */
	public List<User> findByNameLikeAndGroupIdForOwnerTransfer(String name, Integer userId,Integer groupId) {
		String sql = BASE_SQL_FROM_USERS + "WHERE name LIKE :name AND status != 9 AND user_id <> :userId AND "
				+ "user_id IN (SELECT user_id FROM group_relations WHERE group_id = :groupId AND relation_status = 1)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("userId", userId).addValue("groupId", groupId);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.isEmpty()) {
			return null;
		}
		return userList;
	}

	/**
	 * ユーザー情報を更新するメソッド.
	 * 
	 * @param user ユーザー
	 */
	public void update(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "UPDATE users SET name = :name, email = :email, password = :password, image_path = :imagePath, "
				+ "profile = :profile, status = :status, update_password_date = :updatePasswordDate WHERE user_id = :userId;";
		template.update(sql, param);
	}
	
	/**
	 * ユーザ情報を削除するメソッド.
	 * 
	 * @param userId ユーザid
	 */
	public void delete(Integer userId) {
		String sql = "DELETE FROM users WHERE user_id = :userId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		template.update(sql, param);
	}
//	/**
//	 * ユーザidとcategoryIDにてユーザ情報を取得する
//	 * 
//	 * @param userId ユーザid
//	 * @param categoryId カテゴリid
//	 * @return ユーザidとカテゴリidに一致したユーザ情報
//	 */
//	public List<User> findByCategoryId(Integer userId, Integer categoryId){
//		String sql = BASE_SQL_FROM_5 + "WHERE u.user_id = :userId AND o.category_id = :categoryId AND u.status != 9;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("categoryId", categoryId);
//		return template.query(sql, param, USER_RESULT_SET_EXTRACTOR);
//	}
}