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
import com.honcari.domain.Category;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.domain.User;

/**
 * owned_book_infoテーブルを操作するリポジトリ.
 * 
 * @author hatakeyamakouta
 *
 */
@Repository
public class OwnedBookInfoRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
		
	// ownedBookInfoに紐づくユーザー・カテゴリーのマッパー
	private final static RowMapper<OwnedBookInfo> OWNED_BOOK_INFO_ROW_MAPPER = (rs, i) -> {
		OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
		ownedBookInfo.setOwnedBookInfoId(rs.getInt("o_owned_book_info_id"));
		ownedBookInfo.setUserId(rs.getInt("o_user_id"));
		ownedBookInfo.setBookId(rs.getInt("o_book_id"));
		ownedBookInfo.setCategoryId(rs.getInt("o_category_id"));
		ownedBookInfo.setBookStatus(rs.getInt("o_book_status"));
		ownedBookInfo.setComment(rs.getString("o_comment"));
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
		User user = new User();
		user.setUserId(rs.getInt("u_user_id"));
		user.setName(rs.getString("u_name"));
		user.setEmail(rs.getString("u_email"));
		user.setPassword(rs.getString("u_password"));
		user.setImagePath(rs.getString("u_image_path"));
		user.setProfile(rs.getString("u_profile"));
		user.setStatus(rs.getInt("u_status"));
		ownedBookInfo.setUser(user);
		Category category = new Category();
		category.setCategoryId(rs.getInt("c_category_id"));
		category.setName(rs.getString("c_name"));
		ownedBookInfo.setCategory(category);
		return ownedBookInfo;
	};
	
	/**	OWNED_BOOK_INFO_ROW_MAPPERを使用する際に全件取得するためのSELECT文 */
	private static final String SELECT_SQL = "SELECT o.owned_book_info_id o_owned_book_info_id, o.user_id o_user_id, o.book_id o_book_id, o.category_id o_category_id,"
			+ "o.book_status o_book_status, o.comment o_comment, b.book_id b_book_id, b.isbn_id b_isbn_id, b.title b_title, b.author b_author, b.published_date b_published_date,"
			+ "b.description b_description, b.page_count b_page_count, b.thumbnail_path b_thumbnail_path, u.user_id u_user_id, u.name u_name, u.email u_email,"
			+ "u.password u_password, u.image_path u_image_path, u.profile u_profile, u.status u_status, c.category_id c_category_id, c.name c_name FROM owned_book_info o "
			+ "INNER JOIN books b ON o.book_id = b.book_id INNER JOIN users u ON o.user_id = u.user_id INNER JOIN category c ON o.category_id = c.category_id";

	/**
	 * idからユーザーが所有している本の情報を取得する
	 * 
	 * @param ownedBookInfoId ユーザーが所有している本の情報ID
	 * @return ユーザーが所有している本の情報
	 */
	public OwnedBookInfo findByOwnedBookInfoId(Integer ownedBookInfoId) {
		String sql = SELECT_SQL + " WHERE o.owned_book_info_id = :ownedBookInfoId AND o.book_status != 4;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("ownedBookInfoId", ownedBookInfoId);
		OwnedBookInfo ownedBookInfo = template.queryForObject(sql, param, OWNED_BOOK_INFO_ROW_MAPPER);
		return ownedBookInfo;
	}

	
	/**
	 * ユーザーが所有している書籍情報を更新する.
	 * 
	 * @param ownedBookInfoId ユーザーが所有している書籍情報
	 */
	//TODO 下記のeditメソッドと一本化
	public void update(OwnedBookInfo ownedBookInfo) {
		String sql = "UPDATE owned_book_info SET user_id = :userId, book_id = :bookId, category_id = :categoryId, "
				+ "book_status = :bookStatus, comment = :comment WHERE owned_book_info_id = :ownedBookInfoId";
		SqlParameterSource param = new BeanPropertySqlParameterSource(ownedBookInfo);
		template.update(sql, param);
	}
	
	/**
	 * ユーザが所有している書籍情報を編集する
	 * 
	 * @param ownedBookInfo ユーザーが所有している書籍情報
	 */
	public void editOwnedBookInfo(OwnedBookInfo ownedBookInfo) {
		String sql = "UPDATE owned_book_info SET owned_book_info_id = :ownedBookInfoId, user_id = :userId, book_id = :bookId, category_id = :categoryId, book_status = :bookStatus, comment = :comment WHERE owned_book_info_id = :ownedBookInfoId;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(ownedBookInfo);
		template.update(sql, param);
	}
	
//	/**
//	 * ユーザが所有している書籍情報の登録を解除する(bookStatusをdelete(4)に変更する).
//	 * 
//	 * @param bookId 書籍ID
//	 */
//	public void deleteBook(Integer bookId) {
//		String sql = "UPDATE books SET deleted = true WHERE book_id = :bookId;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("bookId", bookId);
//		template.update(sql, param);
//	}
	
	/**
	 * owned_book_infoテーブルにデータを挿入する.
	 * 
	 * @param ownedBookInfo 書籍情報
	 */
	public void insertOwnedBookInfo(OwnedBookInfo ownedBookInfo) {
		String sql = "INSERT INTO owned_book_info(owned_book_info_id, user_id, book_id, category_id, book_status, comment) "
				+ "VALUES(DEFAULT, :userId, :bookId, :categoryId, :bookStatus, :comment);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(ownedBookInfo);
		template.update(sql, param);
	}
	
	/**
	 * ユーザidとcategoryIDにてユーザ情報を取得する.
	 * 
	 * @param userId ユーザid
	 * @param categoryId カテゴリid
	 * @return ユーザidとカテゴリidに一致したユーザ情報
	 */
	public List<OwnedBookInfo> findByCategoryId(Integer userId, Integer categoryId){
		String sql = SELECT_SQL + " WHERE u.user_id = :userId AND o.category_id = :categoryId AND u.status != 9 AND o.book_status != 4;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("categoryId", categoryId);
		return template.query(sql, param, OWNED_BOOK_INFO_ROW_MAPPER);
	}
	
	/**
	 * ユーザーIDから検索するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return ユーザー情報リスト
	 */
	public List<OwnedBookInfo> findByUserId(Integer userId) {
		String sql = SELECT_SQL + " WHERE u.user_id = :userId AND u.status != 9 AND o.book_status != 4;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		return template.query(sql, param, OWNED_BOOK_INFO_ROW_MAPPER);
	}
}