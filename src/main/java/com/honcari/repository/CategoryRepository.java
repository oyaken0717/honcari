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
			
			int nowCategoryId = rs.getInt("c_category_id");
			if(nowCategoryId != beforeCategoryId) {
				Category category = new Category();
				bookList = new ArrayList<>();
				category.setId(rs.getInt("c_category_id"));
				category.setName(rs.getString("c_name"));
				category.setBookList(bookList);
				categoryList.add(category);
			}
			
			User user = new User();
			user.setId(rs.getInt("u_user_id"));
			user.setName(rs.getString("u_name"));
			user.setDeleted(rs.getBoolean("u_deleted"));
			
			Book book = new Book();
			book.setId(rs.getInt("b_book_id"));
			book.setUserId(rs.getInt("b_user_id"));
			book.setIsbnId(rs.getString("b_isbn_id"));
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
	
	/**	CATEGORY_RESULT_SET_EXTRACTORを使用する際に全件取得するためのSELECT文 */
	private static final String SELECT_SQL = "SELECT u.user_id u_user_id, u.name u_name, u.deleted u_deleted,"
			+ "b.book_id b_book_id, b.user_id b_user_id, b.isbn_id b_isbn_id, b.category_id b_category_id, b.title b_title,"
			+ "b.author b_author, b.published_date b_published_date, b.description b_description, b.page_count b_page_count,"
			+ "b.thumbnail_path b_thumbnail_path, b.status b_status, b.comment b_comment, b.deleted b_deleted,"
			+ "c.category_id c_category_id, c.name c_name FROM users u INNER JOIN books b ON u.user_id = b.user_id "
			+ "AND b.deleted <> true INNER JOIN category c ON b.category_id = c.category_id WHERE u.user_id in ("
			+ "SELECT user_id FROM group_relationship WHERE user_id != :userId AND group_id IN ("
			+ "SELECT group_id FROM group_relationship WHERE user_id = :userId)) AND u.deleted = false ORDER BY c.category_id;";
	
	
	/**
	 * ユーザーが所属しているグループ全てのカテゴリ一覧にある本情報を取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return カテゴリ一覧
	 */
	public List<Category> findByUserId(Integer userId) {
		String sql = SELECT_SQL;
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Category> categoryList = template.query(sql, param, CATEGORY_RESULT_SET_EXTRACTOR);
		if (categoryList.isEmpty()) {
			return null;
		}
		return categoryList;
	}
}