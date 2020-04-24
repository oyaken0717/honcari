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
import com.honcari.domain.OwnedBookInfo;
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
	
	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) ->{
		Category category = new Category();
		category.setCategoryId(rs.getInt("category_id"));
		category.setName(rs.getString("name"));
		return category;
	};
	
	private static final ResultSetExtractor<List<Category>> CATEGORY_RESULT_SET_EXTRACTOR = (rs) -> {
		List<OwnedBookInfo> ownedBookInfoList = null;
		List<Category> categoryList = new ArrayList<>();
		int beforeCategoryId = 0;
		while (rs.next()) {
			
			int nowCategoryId = rs.getInt("c_category_id");
			if(nowCategoryId != beforeCategoryId) {
				Category category = new Category();
				ownedBookInfoList = new ArrayList<>();
				category.setCategoryId(rs.getInt("c_category_id"));
				category.setName(rs.getString("c_name"));
				category.setOwnedBookInfoList(ownedBookInfoList);
				categoryList.add(category);
			}
			OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
			User user = new User();
			user.setUserId(rs.getInt("u_user_id"));
			user.setName(rs.getString("u_name"));
			user.setStatus(rs.getInt("u_status"));
			ownedBookInfo.setUser(user);
			
			Book book = new Book();
			book.setBookId(rs.getInt("b_book_id"));
			book.setIsbnId(rs.getString("b_isbn_id"));
			book.setTitle(rs.getString("b_title"));
			book.setAuthor(rs.getString("b_author"));
			book.setPublishedDate(rs.getString("b_published_date"));
			book.setDescription(rs.getString("b_description"));
			book.setPageCount(rs.getInt("b_page_count"));
			book.setThumbnailPath(rs.getString("b_thumbnail_path"));
			ownedBookInfo.setBook(book);
			ownedBookInfoList.add(ownedBookInfo);

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
	 * ユーザーが所属しているグループ全てのカテゴリ一覧にある本情報を取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return カテゴリ一覧
	 */
	public List<Category> findByUserId(Integer userId) {
		String sql = "SELECT u.user_id u_user_id, u.name u_name, u.status u_status,b.book_id b_book_id,"
				+ "b.isbn_id b_isbn_id,b.title b_title,b.author b_author, b.published_date b_published_date,"
				+ "b.description b_description,b.page_count b_page_count,b.thumbnail_path b_thumbnail_path,"
				+ "o.comment o_comment, o.book_status b_book_status,c.category_id c_category_id, c.name c_name "
				+ "FROM users u INNER JOIN owned_book_info o ON u.user_id = o.user_id AND o.book_status != 9 "
				+ "INNER JOIN books b ON o.book_id = b.book_id AND b.deleted = false "
				+ "INNER JOIN category c ON b.category_id = c.category_id "
				+ "WHERE u.status != 9 AND u.user_id in ("
					+ "SELECT user_id FROM group_relationship WHERE user_id != :userId AND group_id IN ("
						+ "SELECT group_id FROM group_relationship WHERE user_id = :userId)) "
				+ "ORDER BY c.category_id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Category> categoryList = template.query(sql, param, CATEGORY_RESULT_SET_EXTRACTOR);
		if (categoryList.isEmpty()) {
			return null;
		}
		return categoryList;
	}
}