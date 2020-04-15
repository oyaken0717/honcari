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
	
	private static final ResultSetExtractor<List<User>> USER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<User> userList = new ArrayList<>();
		List<Book> bookList = new ArrayList<>();

		int beforeUserId = 0;

		while (rs.next()) {
			int nowUserId = rs.getInt("user_id");
			if (nowUserId != beforeUserId) {
				User user = new User();
				bookList = new ArrayList<>();
				user.setId(nowUserId);
				user.setName(rs.getString("user_name"));
				user.setBookList(bookList);
				userList.add(user);
			}
			Book book = new Book();
			book.setId(rs.getInt("book_id"));
			book.setUserId(nowUserId);
			book.setCategoryId(rs.getInt("category_id"));
			book.setTitle(rs.getString("book_title"));
			book.setAuthor(rs.getString("book_author"));
			book.setPublishedDate(rs.getString("book_published_date"));
			book.setDescription(rs.getString("book_description"));
			book.setPageCount(rs.getInt("book_page_count"));
			book.setThumbnailPath(rs.getString("book_thumbnail_path"));
			book.setStatus(rs.getInt("book_status"));
			bookList.add(book);

			Category category = new Category();
			category.setId(rs.getInt("category_id"));
			category.setName(rs.getString("category_name"));
			
			beforeUserId = nowUserId;
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
	
	/**
	 * グループIDからユーザー情報を取得するメソッド
	 * 
	 * @param groupId グループID
	 * @return ユーザー情報リスト
	 */
	public List<User> findByGroupId(Integer groupId) {
		String sql = "SELECT u.user_id, u.name user_name, b.book_id, b.title book_title, b.author book_author, b.published_date "
				+ "book_published_date, b.description book_description, b.page_count book_page_count, "
				+ "b.thumbnail_path book_thumbnail_path, b.status book_status, c.category_id, c.name category_name "
				+ "FROM users u INNER JOIN books b ON u.user_id=b.user_id INNER JOIN category c "
				+ "ON b.category_id=c.category_id INNER JOIN group_relationship gr ON u.user_id=gr.user_id "
				+ "INNER JOIN groups g ON g.group_id=gr.group_id WHERE g.group_id=:groupId ORDER BY u.user_id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		List<User> userList = template.query(sql, param, USER_RESULT_SET_EXTRACTOR);
		if (userList.isEmpty()) {
			return null;
		}
		return userList;
	}

}
