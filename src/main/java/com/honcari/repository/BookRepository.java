package com.honcari.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.honcari.domain.Book;
import com.honcari.domain.Category;
import com.honcari.domain.User;

/**
 * booksテーブルを操作するリポジトリ.
 * 
 * @author yamadadai
 *
 */
@Repository
public class BookRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static ResultSetExtractor<List<Book>> BOOK_RESULT = (rs) -> {
		List<Book> bookList = new ArrayList<>();
		
		int beforeUserId = 0;
		
		while(rs.next()) {
			int nowUserId = rs.getInt("user_id");
			if(nowUserId != beforeUserId) {
				User user = new User();
				bookList = new ArrayList<>();
				user.setId(nowUserId);
				user.setBookList(bookList);
				
				Book book = new Book();
				book.setId(rs.getInt("book_id"));
				book.setUserId(nowUserId);
				book.setCategoryId(rs.getInt("category_id"));
				book.setTitle(rs.getString("book_title"));
				book.setAuthor(rs.getString("book_author"));
				book.setPublished_date(rs.getString("book_published_date"));
				book.setDescription(rs.getString("book_description"));
				book.setPageCount(rs.getInt("book_page_count"));
				book.setThumbnailPath(rs.getString("book_thumbnail_path"));
				book.setStatus(rs.getInt("book_status"));
				bookList.add(book);
				
				Category category = new Category();
				category.setId(rs.getInt("category_id"));
				category.setName(rs.getString("category_name"));
			}
			beforeUserId = nowUserId;
		}
		return bookList;
	};
	
	/**
	 * グループIDから１グループの本情報を取得するメソッド.
	 * 
	 * @param groupId グループID
	 * @return 本情報リスト
	 */
	public List<Book> findByGroupId(Integer groupId) {
		String sql = "SELECT u.user_id, b.book_id, b.title book_title, b.author book_author, b.published_date "
				+ "book_published_date, b.description book_description, b.page_count book_page_count, "
				+ "b.thumbnail_path book_thumbnail_path, b.status book_status, c.category_id, c.name category_name "
				+ "FROM users u INNER JOIN books b ON u.user_id=b.user_id INNER JOIN category c "
				+ "ON b.category_id=c.category_id INNER JOIN group_relationship gr ON u.user_id=gr.user_id "
				+ "INNER JOIN groups g ON g.group_id=gr.group_id WHERE g.group_id=:groupId";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		List<Book> bookList = template.query(sql, param, BOOK_RESULT);
		if(bookList.isEmpty()) {
			return null;
		}
		return bookList;
	}
	
	/**
	 * 引数の書籍情報をbooksテーブルに追加するメソッド.
	 * 
	 * @param book 書籍情報
	 */
	public void insert(Book book) {
		String sql = "INSERT INTO books(id, isbn_id, user_id, category_id, title, author, published_date, description, page_count, thumbnail_path, status"
				+ " VALUES(:id, :isbnId, :userId, :categoryId, :title, :author, :published_date, :description, :page_count, :thumbnail_path, status;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(book);
		template.update(sql, param);
	}
}
