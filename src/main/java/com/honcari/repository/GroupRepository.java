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
import com.honcari.domain.BookLending;
import com.honcari.domain.Group;
import com.honcari.domain.GroupRealationship;
import com.honcari.domain.User;

/**
 * groupsテーブルを操作するリポジトリ.
 * 
 * @author yamadadai
 *
 */
@Repository
public class GroupRepository {

	private static ResultSetExtractor<List<Group>> GROUP_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Group> groupList = new ArrayList<>();
		List<User> userList = new ArrayList<>();
		List<Book> bookList = new ArrayList<>();

		Group group = new Group();
		User user = new User();
		int beforeGroupId = 0;
		int beforeUserId = 0;
		int beforeBookId = 0;

		while (rs.next()) {
			int nowGroupId = rs.getInt("g_group_id");
			if (beforeGroupId != nowGroupId) {
				group = new Group();
				group.setId(rs.getInt("g_group_id"));
				group.setName(rs.getString("g_name"));
				group.setDescription(rs.getString("g_description"));
				group.setUserList(userList);
				groupList.add(group);
				
				beforeGroupId = nowGroupId;
			}

			int nowUserId = rs.getInt("u_user_id");
			if (nowUserId != beforeUserId) {
				user = new User();
				bookList = new ArrayList<>();
				user.setId(nowUserId);
				user.setName(rs.getString("u_name"));
				user.setEmail(rs.getString("u_email"));
				user.setPassword(rs.getString("u_password"));
				user.setImagePath(rs.getString("u_image_path"));
				user.setProfile(rs.getString("u_profile"));
				user.setDeleted(rs.getBoolean("u_deleted"));
				user.setBookList(bookList);
				userList.add(user);

				beforeUserId = nowUserId;
			}

			int bookId = rs.getInt("b_book_id");
			if (bookId != beforeBookId) {
				Book book = new Book();
				book.setId(rs.getInt("b_book_id"));
				book.setIsbnId(rs.getString("b_isbn_id"));
				book.setUserId(rs.getInt("b_user_id"));
				book.setCategoryId(rs.getInt("b_category_id"));
				book.setTitle(rs.getString("b_title"));
				book.setAuthor(rs.getString("b_author"));
				book.setPublishedDate(rs.getString("b_published_date"));
				book.setDescription(rs.getString("b_description"));
				book.setPageCount(rs.getInt("b_page_count"));
				book.setThumbnailPath(rs.getString("b_thumbnail_path"));
				book.setStatus(rs.getInt("b_status"));
				book.setComment(rs.getString("b_comment"));
				book.setDeleted(rs.getBoolean("b_deleted"));
				bookList.add(book);

				beforeBookId = bookId;
			}

		}
		return groupList;
	};

	private static final RowMapper<Group> GROUP_ROW_MAPPER = (rs, i) -> {
		Group group = new Group();
		group.setId(rs.getInt("group_id"));
		group.setName(rs.getString("name"));
		group.setDescription(rs.getString("description"));
		return group;
	};
	
	private static String SQL = "SELECT g.group_id g_group_id, g.name g_name, g.description g_description,"
			+ " u.user_id u_user_id, u.name u_name, u.email u_email, u.password u_password, u.image_path "
			+ "u_image_path, u.profile u_profile, u.deleted u_deleted, b.book_id b_book_id, b.isbn_id b_isbn_id, b.user_id b_user_id, "
			+ "b.category_id b_category_id, b.title b_title, b.author b_author, b.published_date "
			+ "b_published_date, b.description b_description, b.page_count b_page_count, b.thumbnail_path "
			+ "b_thumbnail_path, b.status b_status, b.comment b_comment, b.deleted b_deleted "
			+ "FROM groups g LEFT OUTER JOIN group_relationship gr ON "
			+ "g.group_id = gr.group_id LEFT OUTER JOIN users u ON gr.user_id = u.user_id LEFT OUTER JOIN "
			+ "books b ON u.user_id = b.user_id ";

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
		if (group.getId() == null) {
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

	public List<Group> findByLikeName(String name) {
		String sql = "SELECT group_id,name,description FROM groups WHERE name LIKE :name ORDER BY group_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		return template.query(sql, param, GROUP_ROW_MAPPER);
	}
	
	public Group findByGroupId(Integer groupId) {
		String sql = SQL;
		sql += " WHERE g.group_id = :groupId ORDER BY g.group_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		List<Group> groupList = template.query(sql, param, GROUP_RESULT_SET_EXTRACTOR);
		return groupList.get(0);
	}
}
