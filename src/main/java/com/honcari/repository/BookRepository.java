package com.honcari.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

	/** bookドメインのローマッパー */
	private final static RowMapper<Book> BOOK_ROW_MAPPER = (rs, i) -> {
		Book book = new Book();
		book.setBookId(rs.getInt("b_book_id"));
		book.setIsbnId(rs.getString("b_isbn_id"));
		book.setTitle(rs.getString("b_title"));
		book.setAuthor(rs.getString("b_author"));
		book.setPublishedDate(rs.getString("b_published_date"));
		book.setDescription(rs.getString("b_description"));
		book.setPageCount(rs.getInt("b_page_count"));
		book.setThumbnailPath(rs.getString("b_thumbnail_path"));
		return book;
	};
	
	/**	SELECT文用のSQL定数 */
	private static final String SELECT_SQL = "SELECT book_id, isbn_id, title, author, published_date, description, page_count, thumbnail_path FROM books";
	
	/**
	 * booksテーブルに書籍情報を登録する為のメソッド.
	 * 
	 * @param book
	 */
	public void insert(Book book) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("INSERT INTO books(book_id, isbn_id, title, author, published_date, description, page_count, thumbnail_path) ")
		.append("VALUES(DEFAULT, :isbnId, :title, :author, :publishedDate, :description, :pageCount, :thumbnailPath);");
		String sql = stringBuilder.toString();
		SqlParameterSource param = new BeanPropertySqlParameterSource(book);
		template.update(sql, param);
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