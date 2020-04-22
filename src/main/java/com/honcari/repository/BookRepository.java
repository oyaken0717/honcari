package com.honcari.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
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

	// 本情報に紐づくユーザー・カテゴリーのマッパー
	private final static RowMapper<Book> BOOK_USER_CATEGORY_ROW_MAPPER = (rs, i) -> {
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
		book.setDeleted(rs.getBoolean("book_deleted"));
		User user = new User();
		user.setId(rs.getInt("user_id"));
		user.setName(rs.getString("user_name"));
		user.setEmail(rs.getString("user_email"));
		user.setDeleted(rs.getBoolean("user_deleted"));
		book.setUser(user);
		Category category = new Category();
		category.setId(rs.getInt("category_id"));
		category.setName(rs.getString("category_name"));
		book.setCategory(category);
		return book;
	};

	// 使わなくなったのでコメントアウト、一応残しておく（山田）
//	private static ResultSetExtractor<List<Book>> BOOK_RESULT = (rs) -> {
//		List<Book> bookList = new ArrayList<>();
//
//		int beforeUserId = 0;
//
//		while (rs.next()) {
//			int nowUserId = rs.getInt("user_id");
//			if (nowUserId != beforeUserId) {
//				User user = new User();
//				bookList = new ArrayList<>();
//				user.setId(nowUserId);
//				user.setName(rs.getString("user_name"));
//				user.setBookList(bookList);
//			}
//			Book book = new Book();
//			book.setId(rs.getInt("book_id"));
//			book.setUserId(nowUserId);
//			book.setCategoryId(rs.getInt("category_id"));
//			book.setTitle(rs.getString("book_title"));
//			book.setAuthor(rs.getString("book_author"));
//			book.setPublishedDate(rs.getString("book_published_date"));
//			book.setDescription(rs.getString("book_description"));
//			book.setPageCount(rs.getInt("book_page_count"));
//			book.setThumbnailPath(rs.getString("book_thumbnail_path"));
//			book.setStatus(rs.getInt("book_status"));
//			bookList.add(book);
//
//			Category category = new Category();
//			category.setId(rs.getInt("category_id"));
//			category.setName(rs.getString("category_name"));
//			
//			beforeUserId = nowUserId;
//		}
//		return bookList;
//	};

	/**
	 * グループIDから１グループの本情報を取得するメソッド.
	 * 
	 * @param groupId グループID
	 * @return 本情報リスト
	 */
	// 使わなくなったのでコメントアウト、一応残しておく（山田）
//	public List<Book> findByGroupId(Integer groupId) {
//		String sql = "SELECT u.user_id, u.name user_name, b.book_id, b.title book_title, b.author book_author, b.published_date "
//				+ "book_published_date, b.description book_description, b.page_count book_page_count, "
//				+ "b.thumbnail_path book_thumbnail_path, b.status book_status, c.category_id, c.name category_name "
//				+ "FROM users u INNER JOIN books b ON u.user_id=b.user_id INNER JOIN category c "
//				+ "ON b.category_id=c.category_id INNER JOIN group_relationship gr ON u.user_id=gr.user_id "
//				+ "INNER JOIN groups g ON g.group_id=gr.group_id WHERE g.group_id=:groupId ORDER BY u.user_id";
//
//		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
//		List<Book> bookList = template.query(sql, param, BOOK_RESULT);
//		if (bookList.isEmpty()) {
//			return null;
//		}
//		return bookList;
//	}

	/**
	 * 本IDから本情報と付随するカテゴリ情報・所有者情報を取得する.
	 * 
	 * @param bookId 本ID
	 * @return 本情報＋カテゴリ情報＋所有者情報
	 */
	public Book findByBookId(Integer bookId) {
		String sql = "SELECT u.user_id user_id, u.name user_name, u.email user_email, u.deleted user_deleted, b.book_id book_id, b.isbn_id book_isbn_id,"
				+ "b.title book_title, b.author book_author, b.published_date book_published_date, "
				+ "b.description book_description, b.page_count book_page_count, b.thumbnail_path book_thumbnail_path, "
				+ "b.status book_status, b.comment book_comment, b.deleted book_deleted, c.category_id category_id, "
				+ "c.name category_name FROM users u INNER JOIN books b ON u.user_id = b.user_id INNER JOIN "
				+ "category c ON b.category_id = c.category_id WHERE book_id = :bookId AND book_deleted = false AND user_deleted = false;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("bookId", bookId);
		Book book = template.queryForObject(sql, param, BOOK_USER_CATEGORY_ROW_MAPPER);
		return book;
	}

	/**
	 * 引数の書籍情報をbooksテーブルに追加するメソッド.
	 * 
	 * @param book 書籍情報
	 */
	public void insert(Book book) {
		String sql = "INSERT INTO books(book_id, isbn_id, user_id, category_id, title, author, published_date, description, page_count, thumbnail_path, status, comment)"
				+ " VALUES(DEFAULT, :isbnId, :userId, :categoryId, :title, :author, :publishedDate, :description, :pageCount, :thumbnailPath, :status, :comment);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(book);
		template.update(sql, param);
	}

	/**
	 * 本の貸出状況を更新する.
	 * 
	 * @param status 貸出状況
	 * @param bookId 本ID
	 */
	public void updateStatus(Integer status, Integer bookId) {
		String sql = "UPDATE books SET status = :status WHERE book_id = :bookId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("status", status).addValue("bookId", bookId);
		template.update(sql, param);
	}
	
	/**
	 * 書籍情報を編集する(カテゴリid,コメント)
	 * 
	 * @param bookId 書籍ID
	 * @param categoryId カテゴリID
	 * @param comment コメント
	 */
	public void editBook(Integer bookId, Integer categoryId, String comment) {
		String sql = "UPDATE books SET category_id = :categoryId, comment = :comment WHERE book_id = :bookId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("bookId", bookId).addValue("categoryId", categoryId).addValue("comment", comment);
		template.update(sql, param);
	}
	
	/**
	 * 書籍情報を削除する(deletedフラグをtrueに変更する).
	 * 
	 * @param bookId 書籍ID
	 */
	public void deleteBook(Integer bookId) {
		String sql = "UPDATE books SET deleted = true WHERE book_id = :bookId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("bookId", bookId);
		template.update(sql, param);
	}
}