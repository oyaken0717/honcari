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
import com.honcari.domain.Category;
import com.honcari.domain.Group;
import com.honcari.domain.User;

@Repository
public class UserRepository {

	private static final RowMapper<User> User_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("user_id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setImagePath("image_path");
		user.setProfile("profile");
		return user;
	};
	
	private static ResultSetExtractor<List<User>> USER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<User>userList = new ArrayList<>();
		List<Book> bookList = new ArrayList<>();
		List<Group> groupList = new ArrayList<>();
		User user = new User();
		int beforeUserId = 0;
		int beforeBookId = 0;
		int beforeGroupId = 0;

		while (rs.next()) {
			int nowUserId = rs.getInt("u_user_id");
			if (nowUserId != beforeUserId) {
				bookList = new ArrayList<>();
				user.setId(nowUserId);
				user.setName(rs.getString("u_name"));
				user.setEmail(rs.getString("u_email"));
				user.setPassword(rs.getString("u_password"));
				user.setImagePath(rs.getString("u_image_path"));
				user.setProfile(rs.getString("u_profile"));
				user.setBookList(bookList);
				user.setGroupList(groupList);
				userList.add(user);
				
				beforeUserId = nowUserId;
				}
			
			int bookId = rs.getInt("b_book_id");
			if(bookId != beforeBookId) {
				Book book = new Book();
				book.setId(rs.getInt("b_book_id"));
				book.setIsbnId(rs.getLong("b_isbn_id"));
				book.setUserId(rs.getInt("b_user_id"));
				book.setCategoryId(rs.getInt("b_category_id"));
				book.setTitle(rs.getString("b_title"));
				book.setAuthor(rs.getString("b_author"));
				book.setPublishedDate(rs.getString("b_published_date"));
				book.setDescription(rs.getString("b_description"));
				book.setPageCount(rs.getInt("b_page_count"));
				book.setThumbnailPath(rs.getString("b_thumbnail_path"));
				book.setStatus(rs.getInt("b_status"));
				bookList.add(book);
				
				beforeBookId = bookId;
			}
			
			int groupId = rs.getInt("g_group_id");
			if(groupId != beforeGroupId) {
				Group group = new Group();
				group.setId(rs.getInt("g_group_id"));
				group.setName(rs.getString("g_name"));
				group.setDescription(rs.getString("g_description"));
				groupList.add(group);
				
				beforeGroupId = groupId;
			}
		}
		return userList;
	};
	@Autowired
	private NamedParameterJdbcTemplate template;

	public void insert(User user) {
		String sql = "INSERT INTO Users(name,email,password,image_path,profile)VALUES(:name,:email,:password,:imagePath,:profile)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}

	public User findByEmail(String email) {
		String sql = "SELECT user_id,name,email,password,image_path,profile from Users where email = :email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, User_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	public User findUserInfoByUserId(Integer userId) {
		String sql = "SELECT u.user_id u_user_id,u.name u_name,u.email u_email,u.password u_password,u.image_path u_image_path,u.profile u_profile,"
				+ "b.book_id b_book_id,b.isbn_id b_isbn_id,b.user_id b_user_id, b.category_id b_category_id, b.title b_title, b.author b_author, "
				+ "b.published_date b_published_date, b.description b_description, b.page_count b_page_count, b.thumbnail_path b_thumbnail_path, "
				+ "b.status b_status, g.group_id g_group_id,g.name g_name,g.description g_description FROM users u INNER JOIN books b ON u.user_id = b.user_id "
				+ "INNER JOIN group_relationship gr ON u.user_id = gr.user_id INNER JOIN groups g ON g.group_id=gr.group_id WHERE u.user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<User> userList = template.query(sql,param,USER_RESULT_SET_EXTRACTOR);
		return userList.get(0);

	}
}
