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
import com.honcari.domain.BookRental;
import com.honcari.domain.Category;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.domain.User;

/**
 * 本の貸し借り状況を操作するリポジトリ.
 * 
 * @author shumpei
 *
 */
@Repository
public class BookRentalRepository {

	private final static String SQL = "SELECT br.book_rental_id br_book_rental_id, br.owned_booK_info_id br_owned_booK_info_id, "
			+ "br.borrow_user_id br_borrow_user_id, br.request_beginning br_request_beginning, br.beginning br_beginning, "
			+ "br.request_deadline br_request_deadline, br.deadline br_deadline, "
			+ "br.rental_status br_rental_status, br.version br_version, br.approval_date br_approval_date, "
			// ユーザーが保有している本の情報
			+ "ob.owned_booK_info_id ob_owned_booK_info_id, ob.user_id ob_user_id, ob.book_id ob_book_id, ob.category_id "
			+ "ob_category_id, ob.book_status ob_book_status, ob.comment ob_comment, ob.version ob_version, "
			// 本情報
			+ "b.book_id b_book_id, b.isbn_id b_isbn_id, b.title b_title, b.author b_author, "
			+ "b.published_date b_published_date, b.description b_description, b.page_count b_page_count, "
			+ "b.thumbnail_path b_thumbnail_path, "
			// カテゴリ情報
			+ "c.category_id c_category_id, c.name c_name,"
			// 所有者ユーザー情報
			+ "u1.user_id u1_user_id, u1.name u1_name, u1.email "
			+ "u1_email, u1.password u1_password, u1.image_path u1_image_path, u1.profile u1_profile,"
			// 借り手ユーザー情報
			+ "u2.user_id u2_user_id, u2.name u2_name, u2.email "
			+ "u2_email, u2.password u2_password, u2.image_path u2_image_path, u2.profile u2_profile "
			// テーブル選択
			+ "FROM book_rentals br "
			// book_ownerと結合
			+ "INNER JOIN owned_book_info ob ON br.owned_book_info_id = ob.owned_book_info_id "
			// bookと結合
			+ "INNER JOIN books b ON ob.book_id = b.book_id "
			// カテゴリーと結合
			+ "INNER JOIN category c ON ob.category_id = c.category_id "
			// lendUser（u1)の結合
			+ "INNER JOIN users u1 ON ob.user_id = u1.user_id "
			// borrowUser（u2)の結合
			+ "INNER JOIN users u2 ON br.borrow_user_id = u2.user_id ";

	public final static RowMapper<BookRental> BOOK_RENTAL_ROW_MAPPER = (rs, i) -> {
		// レンタル情報をインスタンス化
		BookRental bookRental = new BookRental();
		bookRental.setBookRentalId(rs.getInt("br_book_rental_id"));
		bookRental.setOwnedBookInfoId(rs.getInt("br_owned_booK_info_id"));
		bookRental.setBorrowUserId(rs.getInt("br_borrow_user_id"));
		bookRental.setRentalStatus(rs.getInt("br_rental_status"));
		bookRental.setRequestBeginning(rs.getDate("br_request_beginning"));
		bookRental.setBeginning(rs.getDate("br_beginning"));
		bookRental.setRequestDeadline(rs.getDate("br_request_deadline"));
		bookRental.setDeadline(rs.getDate("br_deadline"));
		bookRental.setVersion(rs.getInt("br_version"));
		bookRental.setApprovalDate(rs.getTimestamp("br_approval_date"));
		// 所有情報をインスタンス化
		OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
		ownedBookInfo.setOwnedBookInfoId(rs.getInt("ob_owned_booK_info_id"));
		ownedBookInfo.setUserId(rs.getInt("ob_user_id"));
		ownedBookInfo.setBookId(rs.getInt("ob_book_id"));
		ownedBookInfo.setCategoryId(rs.getInt("ob_category_id"));
		ownedBookInfo.setBookStatus(rs.getInt("ob_book_status"));
		ownedBookInfo.setComment(rs.getString("ob_comment"));
		ownedBookInfo.setVersion(rs.getInt("ob_version"));
		// 本情報をインスタンス化
		Book book = new Book();
		book.setBookId(rs.getInt("b_book_id"));
		book.setIsbnId(rs.getString("b_isbn_id"));
		book.setTitle(rs.getString("b_title"));
		book.setAuthor(rs.getString("b_author"));
		book.setPublishedDate(rs.getString("b_published_date"));
		book.setDescription(rs.getString("b_description"));
		book.setPageCount(rs.getString("b_page_count"));
		book.setThumbnailPath(rs.getString("b_thumbnail_path"));
		// カテゴリー情報をインスタンス化
		Category category = new Category();
		category.setCategoryId(rs.getInt("c_category_id"));
		category.setName(rs.getString("c_name"));
		// 所有者ユーザーをインスタンス化
		User owner = new User();
		owner.setUserId(rs.getInt("u1_user_id"));
		owner.setName(rs.getString("u1_name"));
		owner.setEmail(rs.getString("u1_email"));
		owner.setPassword(rs.getString("u1_password"));
		owner.setImagePath(rs.getString("u1_image_path"));
		owner.setProfile(rs.getString("u1_profile"));
		// 所有情報にセット
		ownedBookInfo.setBook(book);
		ownedBookInfo.setUser(owner);
		ownedBookInfo.setCategory(category);
		// 借り手ユーザーをインスタンス化
		User borrowUser = new User();
		borrowUser.setUserId(rs.getInt("u2_user_id"));
		borrowUser.setName(rs.getString("u2_name"));
		borrowUser.setEmail(rs.getString("u2_email"));
		borrowUser.setPassword(rs.getString("u2_password"));
		borrowUser.setImagePath(rs.getString("u2_image_path"));
		borrowUser.setProfile(rs.getString("u2_profile"));
		// レンタル情報にセット
		bookRental.setOwnedBookInfo(ownedBookInfo);
		bookRental.setBorrowUser(borrowUser);
		return bookRental;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * IDからレンタル情報を取得する.
	 * 
	 * @param bookRentalId レンタル情報ID
	 * @return　レンタル情報
	 */
	public BookRental load(Integer bookRentalId) {
		String sql = SQL + "WHERE br.book_rental_id = :bookRentalId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("bookRentalId", bookRentalId);
		return template.queryForObject(sql, param, BOOK_RENTAL_ROW_MAPPER);
	}
	

	/**
	 * 本のレンタル情報を登録する.
	 * 
	 * @param bookRental レンタル情報
	 */
	public void insert(BookRental bookRental) {
		String sql = "INSERT INTO book_rentals (owned_book_info_id, borrow_user_id, rental_status, request_beginning, request_deadline, creation_date ,creation_user) "
				+ "VALUES (:ownedBookInfoId, :borrowUserId, :rentalStatus, :requestBeginning, :requestDeadline, (SELECT NOW()), :creationUserName)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(bookRental);
		template.update(sql, param);
	}

	/**
	 * 本の貸出状況を更新する.
	 * 
	 * @param bookRental レンタル情報
	 */
	public int update(BookRental bookRental) {
		String sql = "UPDATE book_rentals SET owned_book_info_id = :ownedBookInfoId, borrow_user_id = :borrowUserId, "
				+ "rental_status = :rentalStatus, request_beginning = :requestBeginning, beginning = :beginning, "
				+ "request_deadline = :requestDeadline, deadline = :deadline, "
				+ "update_date = (SELECT NOW()), update_user = :updateUserName, "
				+ "version = (:version + 1), approval_date = :approvalDate WHERE book_rental_id = :bookRentalId AND version = :version";
		SqlParameterSource param = new BeanPropertySqlParameterSource(bookRental);
		return template.update(sql, param);
	}

	/**
	 * 本の所有者のユーザーIDからレンタル情報を取得する.
	 * 
	 * @param ownerUserId 所有者ユーザーID
	 * @return レンタル情報
	 */
	public List<BookRental> findByOwnerUserIdAndMultiRentalStatus(Integer ownerUserId) {
		String strSql = SQL;
		strSql = strSql + " WHERE u1.user_id = :ownerUserId AND (br.rental_status = 0 "
				+ "OR br.rental_status = 1 OR br.rental_status = 2 OR br.rental_status = 4) ORDER BY br.book_rental_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("ownerUserId", ownerUserId);
		List<BookRental> bookRentalList = template.query(strSql, param, BOOK_RENTAL_ROW_MAPPER);
		return bookRentalList;
	}

	/**
	 * 本の所有者のユーザーIDとレンタル状況からレンタル情報を取得する.
	 * 
	 * @param ownerUserId  所有者ユーザーID
	 * @param rentalStatus レンタル状況
	 * @return レンタル情報
	 */
	public List<BookRental> findByOwnerUserIdAndRentalStatus(Integer ownerUserId, Integer rentalStatus) {
		String strSql = SQL;
		strSql = strSql
				+ " WHERE u1.user_id = :ownerUserId AND br.rental_status = :rentalStatus ORDER BY br.book_rental_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("ownerUserId", ownerUserId)
				.addValue("rentalStatus", rentalStatus);
		List<BookRental> bookRentalList = template.query(strSql, param, BOOK_RENTAL_ROW_MAPPER);
		return bookRentalList;
	}

	/**
	 * 本の所有者のユーザーIDとレンタル状況から承認順にレンタル情報を取得する.
	 * 
	 * @param ownerUserId  所有者ユーザーID
	 * @param rentalStatus レンタル状況
	 * @return レンタル情報
	 */
	public List<BookRental> findByOwnerUserIdAndRentalStatusOrderByApprovalDate(Integer ownerUserId, Integer rentalStatus) {
		String strSql = SQL;
		strSql = strSql
				+ " WHERE u1.user_id = :ownerUserId AND br.rental_status = :rentalStatus ORDER BY br.approval_date DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("ownerUserId", ownerUserId)
				.addValue("rentalStatus", rentalStatus);
		List<BookRental> bookRentalList = template.query(strSql, param, BOOK_RENTAL_ROW_MAPPER);
		return bookRentalList;
	}

	/**
	 * 本の借り手のユーザーIDからレンタル情報を取得する.
	 * 
	 * @param borrowUserId 借り手ユーザー情報
	 * @return レンタル情報
	 */
	public List<BookRental> findByBorrowUserIdAndMultiRentalStatus(Integer borrowUserId) {
		String strSql = SQL;
		strSql = strSql + " WHERE br.borrow_user_id = :borrowUserId AND (br.rental_status = 0 "
				+ "OR br.rental_status = 1 OR br.rental_status = 2 OR br.rental_status = 4) ORDER BY br.book_rental_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("borrowUserId", borrowUserId);
		List<BookRental> bookRentalList = template.query(strSql, param, BOOK_RENTAL_ROW_MAPPER);
		return bookRentalList;
	}

	/**
	 * 本の借り手のユーザーIDとレンタル状況からレンタル情報を取得する.
	 * 
	 * @param borrowUserId 借り手ユーザーID
	 * @param rentalStatus レンタル状況
	 * @return レンタル情報
	 */
	public List<BookRental> findByBorrowUserIdAndRentalStatus(Integer borrowUserId, Integer rentalStatus) {
		String strSql = SQL;
		strSql = strSql
				+ " WHERE br.borrow_user_id = :borrowUserId AND br.rental_status = :rentalStatus ORDER BY br.book_rental_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("borrowUserId", borrowUserId)
				.addValue("rentalStatus", rentalStatus);
		List<BookRental> bookRentalList = template.query(strSql, param, BOOK_RENTAL_ROW_MAPPER);
		return bookRentalList;
	}

	/**
	 * 本の借り手のユーザーIDとレンタル状況から承認順にレンタル情報を取得する.
	 * 
	 * @param borrowUserId 借り手ユーザーID
	 * @param rentalStatus レンタル状況
	 * @return レンタル情報
	 */
	public List<BookRental> findByBorrowUserIdAndRentalStatusOrderByApprovalDate(Integer borrowUserId, Integer rentalStatus) {
		String strSql = SQL;
		strSql = strSql
				+ " WHERE br.borrow_user_id = :borrowUserId AND br.rental_status = :rentalStatus ORDER BY br.approval_date DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("borrowUserId", borrowUserId)
				.addValue("rentalStatus", rentalStatus);
		List<BookRental> bookRentalList = template.query(strSql, param, BOOK_RENTAL_ROW_MAPPER);
		return bookRentalList;
	}
	
	/**
	 * book_rentalsテーブルから該当ユーザidを削除するメソッド.
	 * 
	 * @param userId ユーザid
	 */
	public void delete(Integer userId) {
		String sql = "DELETE FROM book_rentals WHERE borrow_user_id = :userId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		template.update(sql, param);
	}
}
