package com.honcari.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.honcari.domain.Book;
import com.honcari.domain.Category;
import com.honcari.domain.User;

/**
 * categoryテーブルを操作するリポジトリ.
 * 
 * @author hatakeyamakouta
 *
 */
@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	// カテゴリのローマッパー
	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) ->{
		Category category = new Category();
		category.setId(rs.getInt("category_id"));
		category.setName(rs.getString("name"));
		return category;
	};
	
	private static final ResultSetExtractor<List<Category>> CATEGORY_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Book> bookList = new ArrayList<>();
		List<Category> categoryList = new ArrayList<>();

		int beforeCategoryId = 0;

		while (rs.next()) {
			
			int nowCategoryId = rs.getInt("category_id");
			if(nowCategoryId != beforeCategoryId) {
				Category category = new Category();
				bookList = new ArrayList<>();
				category.setId(rs.getInt("category_id"));
				category.setName(rs.getString("category_name"));
				category.setBookList(bookList);
				categoryList.add(category);
			}
			
			User user = new User();
			user.setId(rs.getInt("user_id"));
			user.setName(rs.getString("user_name"));
			user.setDeleted(rs.getBoolean("user_deleted"));
			
			Book book = new Book();
			book.setId(rs.getInt("book_id"));
			book.setIsbnId(rs.getString("isbn_id"));
			book.setUserId(rs.getInt("user_id"));
			book.setCategoryId(rs.getInt("category_id"));
			book.setTitle(rs.getString("book_title"));
			book.setAuthor(rs.getString("book_author"));
			book.setPublishedDate(rs.getString("book_published_date"));
			book.setDescription(rs.getString("book_description"));
			book.setPageCount(rs.getInt("book_page_count"));
			book.setThumbnailPath(rs.getString("book_thumbnail_path"));
			book.setStatus(rs.getInt("book_status"));
			book.setComment(rs.getString("book_comment"));
			book.setDeleted(rs.getBoolean("book_boolean"));
			bookList.add(book);
			book.setUser(user);

			beforeCategoryId = nowCategoryId;
		}
		return categoryList;
	};
	
	/**
	 * カテゴリ一覧を取得するメソッド.
	 * 
	 * @return カテゴリ一覧
	 */
	public List<Category> findAll(){
		String sql = "SELECT category_id, name FROM category;";
		return template.query(sql, CATEGORY_ROW_MAPPER);
	}
	
	/**
	 * グループごとのカテゴリ一覧を取得するメソッド.
	 * 
	 * @param groupId グループID
	 * @return カテゴリ一覧
	 */
	public List<Category> findByGroupId(Integer groupId) {
		String sql = "SELECT u.user_id, u.name user_name, u.deleted user_deleted, b.book_id, b.isbn_id b_isbn_id, b.title book_title, "
				+ "b.author book_author, b.published_date book_published_date, b.description book_description, b.page_count book_page_count, "
				+ "b.thumbnail_path book_thumbnail_path, b.status book_status, b.comment book_comment, b.deleted book_deleted, "
				+ "c.category_id, c.name category_name "
				+ "FROM users u INNER JOIN books b ON u.user_id=b.user_id INNER JOIN category c "
				+ "ON b.category_id=c.category_id INNER JOIN group_relationship gr ON u.user_id=gr.user_id "
				+ "INNER JOIN groups g ON g.group_id=gr.group_id WHERE g.group_id=:groupId AND book_deleted = false AND user_deleted = false ORDER BY c.category_id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		List<Category> categoryList = template.query(sql, param, CATEGORY_RESULT_SET_EXTRACTOR);
		if (categoryList.isEmpty()) {
			return null;
		}
		return categoryList;
	}
}