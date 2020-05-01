package com.honcari.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.honcari.domain.Book;

/**
 * booksテーブルを操作するリポジトリ.
 * 
 * @author hatakeyamakouta
 *
 */
@Repository
public class BookRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert insert;
	
	/** bookドメインのローマッパー */
	private final static RowMapper<Book> BOOK_ROW_MAPPER = (rs, i) -> {
		Book book = new Book();
		book.setBookId(rs.getInt("book_id"));
		book.setIsbnId(rs.getString("isbn_id"));
		book.setTitle(rs.getString("title"));
		book.setAuthor(rs.getString("author"));
		book.setPublishedDate(rs.getString("published_date"));
		book.setDescription(rs.getString("description"));
		book.setPageCount(rs.getString("page_count"));
		book.setThumbnailPath(rs.getString("thumbnail_path"));
		return book;
	};
	
	/**	SELECT文用のSQL定数 */
	private static final String SELECT_SQL = "SELECT book_id, isbn_id, title, author, published_date, description, page_count, thumbnail_path FROM books";
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcTemplate());
		SimpleJdbcInsert withtableName = simpleJdbcInsert.withTableName("books");
		insert = withtableName.usingGeneratedKeyColumns("book_id");
	}
	
	/**
	 * booksテーブルにデータを挿入する
	 * 
	 * @param book 書籍情報
	 * @return 書籍情報
	 */
	public Book insertBook(Book book) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(book);
		if (book.getBookId() == null) {
			Number key = insert.executeAndReturnKey(param);
			book.setBookId(key.intValue());
			return book;
		}
		return null;
	}
	
	/**
	 * isbnコードより書籍情報を取得する為のメソッド.
	 * 
	 * @param isbnId isbnコード
	 * @return isbnコードに一致した書籍情報
	 */
	public List<Book> findByIsbnId(String isbnId) {
		String sql = SELECT_SQL + " WHERE isbn_id = :isbnId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("isbnId", isbnId);
		return template.query(sql, param, BOOK_ROW_MAPPER);
	}
}