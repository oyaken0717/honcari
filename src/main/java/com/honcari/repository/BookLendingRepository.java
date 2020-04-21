package com.honcari.repository;

import java.sql.Date;
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
import com.honcari.domain.BookLending;
import com.honcari.domain.Category;
import com.honcari.domain.Group;
import com.honcari.domain.User;

/**
 * 本の貸し借り状況を操作するリポジトリ.
 * 
 * @author shumpei
 *
 */
@Repository
public class BookLendingRepository {
	private final static String SQL = "SELECT br.id br_book_lending_id,br.lend_user_id br_lend_user_id,"
			+ "br.borrow_user_id br_borrow_user_id, br.book_id br_book_id, br.deadline br_deadline,"
			+ "br.lending_status br_lending_status, "
			// 本情報
			+ "b.book_id b_book_id,b.isbn_id b_isbn_id,b.user_id b_user_id,b.category_id b_category_id, b.title b_title, "
			+ "b.author b_author, b.published_date b_published_date, b.description b_description, "
			+ "b.page_count b_page_count, b.thumbnail_path b_thumbnail_path, b.status b_status,"
			// カテゴリ情報
			+ "c.category_id c_category_id,c.name c_name,"
			// bookLendingのlendUserIdを元にlendUserドメインを取得↓
			+ "u1.user_id u1_user_id,u1.name u1_name,u1.email "
			+ "u1_email,u1.password u1_password,u1.image_path u1_image_path,u1.profile u1_profile,"
			// 貸し手のグループ情報
			+ "g1.group_id g1_group_id,g1.name g1_name,g1.description g1_description,"
			// bookLendingのborrowUserIdを元にborrowUserドメインを取得↓
			+ "u2.user_id u2_user_id,u2.name u2_name,u2.email "
			+ "u2_email,u2.password u2_password,u2.image_path u2_image_path,u2.profile u2_profile,"
			// 借り手のグループ情報
			+ "g2.group_id g2_group_id,g2.name g2_name,g2.description g2_description "
			// テーブル選択
			+ "FROM book_lending br "
			// bookと結合↓
			+ "INNER JOIN books b ON br.book_id = b.book_id "
			// カテゴリーと結合
			+ "INNER JOIN category c ON b.category_id = c.category_id "
			// lendUser（u1)の結合↓
			+ "INNER JOIN users u1 ON br.lend_user_id = u1.user_id "
			// グループと結合
			+ "INNER JOIN group_relationship gr1 ON u1.user_id = gr1.user_id INNER JOIN groups g1 ON g1.group_id = gr1.group_id "
			// borrowUser（u2)の結合↓
			+ "INNER JOIN users u2 ON br.borrow_user_id = u2.user_id "
			// グループと結合
			+ "INNER JOIN group_relationship gr2 ON u2.user_id = gr2.user_id INNER JOIN groups g2 ON g2.group_id = gr2.group_id ";

	private static ResultSetExtractor<List<BookLending>> BR_RESULT_SET_EXTRACTOR = (rs) -> {
		List<BookLending> bookLendingList = new ArrayList<>();
		List<Group> lenderGroupList = null;
		List<Group> borrowerGroupList = null;
		int beforeBrId = 0;
		int beforeLenderGroupId = 0;
		int beforeBorrowerGroupId = 0;

		while (rs.next()) {
			int nowBrId = rs.getInt("br_book_lending_id");
			if (nowBrId != beforeBrId) {
				// 貸し借り情報をインスタンス化
				BookLending bookLending = new BookLending();
				bookLending.setBookLendingId(nowBrId);
				bookLending.setLendUserId(rs.getInt("br_lend_user_id"));
				bookLending.setBorrowUserId(rs.getInt("br_borrow_user_id"));
				bookLending.setBookId(rs.getInt("br_book_id"));
				bookLending.setDeadline(rs.getDate("br_deadline"));
				bookLending.setLendingStatus(rs.getInt("br_lending_status"));
				bookLending.setBookId(rs.getInt("br_book_lending_id"));
				bookLending.setLendUserId(rs.getInt("br_lend_user_id"));
				bookLendingList.add(bookLending);
				// 本情報をインスタンス化
				Book book = new Book();
				book.setId(rs.getInt("b_book_id"));
				book.setIsbnId(rs.getLong("b_isbn_id"));
				book.setUserId(rs.getInt("b_user_id"));
				book.setCategoryId(rs.getInt("b_category_id"));
				book.setTitle(rs.getString("b_title"));
				book.setAuthor(rs.getString("b_author"));
				book.setPublishedDate(rs.getString("b_published_date"));
				book.setDescription(rs.getString("b_description"));
				book.setPageCount(rs.getInt("b_page_count"));
				book.setThumbnailPath(rs.getString("b_thumbnail_path"));
				book.setStatus(rs.getInt("b_status"));
				// カテゴリー情報をインスタンス化
				Category category = new Category();
				category.setId(rs.getInt("c_category_id"));
				category.setName(rs.getString("c_name"));
				book.setCategory(category);
				bookLending.setBook(book);
				// 貸し手ユーザーをインスタンス化
				User lendUser = new User();
				lenderGroupList = new ArrayList<>();
				lendUser.setId(rs.getInt("u1_user_id"));
				lendUser.setName(rs.getString("u1_name"));
				lendUser.setEmail(rs.getString("u1_email"));
				lendUser.setPassword(rs.getString("u1_password"));
				lendUser.setImagePath(rs.getString("u1_image_path"));
				lendUser.setProfile(rs.getString("u1_profile"));
				lendUser.setGroupList(lenderGroupList);
				bookLending.setLendUser(lendUser);
				// 借り手ユーザーをインスタンス化
				User borrowUser = new User();
				borrowerGroupList = new ArrayList<>();
				borrowUser.setId(rs.getInt("u2_user_id"));
				borrowUser.setName(rs.getString("u2_name"));
				borrowUser.setEmail(rs.getString("u2_email"));
				borrowUser.setPassword(rs.getString("u2_password"));
				borrowUser.setImagePath(rs.getString("u2_image_path"));
				borrowUser.setProfile(rs.getString("u2_profile"));
				borrowUser.setGroupList(borrowerGroupList);
				bookLending.setBorrowUser(borrowUser);
			}
			int lenderGroupId = rs.getInt("g1_group_id");
			// 貸し手ユーザーグループを取得
			if (lenderGroupId != beforeLenderGroupId) {
				Group lenderGroup = new Group();
				lenderGroup.setId(lenderGroupId);
				lenderGroup.setName(rs.getString("g1_name"));
				lenderGroup.setDescription(rs.getString("g1_description"));
				lenderGroupList.add(lenderGroup);
			}
			// 借り手ユーザーグループを取得
			int borrowerGroupId = rs.getInt("g2_group_id");
			if (lenderGroupId != beforeBorrowerGroupId) {
				Group borrowerGroup = new Group();
				borrowerGroup.setId(borrowerGroupId);
				borrowerGroup.setName(rs.getString("g2_name"));
				borrowerGroup.setDescription(rs.getString("g2_description"));
				borrowerGroupList.add(borrowerGroup);
			}
			beforeLenderGroupId = lenderGroupId;
			beforeBorrowerGroupId = borrowerGroupId;
			beforeBrId = nowBrId;
		}
		return bookLendingList;
	};

	private static final RowMapper<BookLending> BOOK_LENDING_ROW_MAPPER = (rs, i) -> {
		BookLending bookLending = new BookLending();
		bookLending.setBookId(rs.getInt("id"));
		bookLending.setLendUserId(rs.getInt("lend_user_id"));
		bookLending.setBorrowUserId(rs.getInt("borrow_user_id"));
		bookLending.setBookId(rs.getInt("book_id"));
		bookLending.setDeadline(rs.getDate("deadline"));
		bookLending.setLendingStatus(rs.getInt("lending_status"));
		return bookLending;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 本の貸出状況を登録する.
	 * 
	 * @param bookId       本ID
	 * @param lendUserId   貸し手ユーザーID
	 * @param borrowUserId 借り手ユーザーID
	 * @param deadline     貸出期限
	 * @param status       貸出状況
	 * 
	 */
	public void insert(Integer bookId, Integer lendUserId, Integer borrowUserId, Date deadline, Integer status) {
		String sql = "INSERT INTO book_lending (lend_user_id, borrow_user_id, book_id, deadline, lending_status) "
				+ "VALUES (:lendUserId, :borrowUserId, :bookId, :deadline, :status)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("bookId", bookId)
				.addValue("lendUserId", lendUserId).addValue("borrowUserId", borrowUserId)
				.addValue("deadline", deadline).addValue("status", status);
		template.update(sql, param);
	}

	/**
	 * 本の貸出状況を更新する.
	 * 
	 * @param bookLending 本貸出に関する情報
	 */
	public void update(BookLending bookLending) {
		String sql = "UPDATE book_lending SET lending_status = :lendingStatus WHERE id = :bookLendingId";
		SqlParameterSource param = new BeanPropertySqlParameterSource(bookLending);
		template.update(sql, param);
	}

	/**
	 * 賃借情報一覧を取得する. 貸し手（所有者）側からの情報
	 * 
	 * @param lendUserId    貸し手ユーザーID
	 * @param lendingStatus 貸出状況
	 * @return 貸借情報一覧
	 */
	public List<BookLending> findByLendUserIdAndLendingStatus(Integer lendUserId) {
		String strSql = SQL;
		strSql = strSql + " WHERE br.lend_user_id = :lendUserId AND (br.lending_status = 0 "
				+ "OR br.lending_status = 1 OR br.lending_status = 2) ORDER BY br.id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("lendUserId", lendUserId);
		List<BookLending> bookLendingList = template.query(strSql, param, BR_RESULT_SET_EXTRACTOR);
		return bookLendingList;
	}

	/**
	 * 貸出承認待ちの賃借情報一覧を取得する. 貸し手（所有者）側からの情報
	 * 
	 * @param lendUserId    貸し手ユーザーID
	 * @param lendingStatus 貸出状況
	 * @return 貸借情報一覧
	 */
	public List<BookLending> findByLendUserIdAndLendingStatus(Integer lendUserId, Integer lendingStatus) {
		String strSql = SQL;
		strSql = strSql + " WHERE br.lend_user_id = :lendUserId AND br.lending_status = :lendingStatus";
		SqlParameterSource param = new MapSqlParameterSource().addValue("lendUserId", lendUserId)
				.addValue("lendingStatus", lendingStatus);
		List<BookLending> bookLendingList = template.query(strSql, param, BR_RESULT_SET_EXTRACTOR);
		return bookLendingList;
	}

	/**
	 * 賃借情報一覧を取得する. 借り手側からの情報
	 * 
	 * @param borrowUserId 借り手ユーザーID
	 * @return 貸出情報一覧
	 */
	public List<BookLending> findByBorrowUserIdAndLendingStatus(Integer borrowUserId) {
		String strSql = SQL;
		strSql = strSql + " WHERE br.borrow_user_id = :borrowUserId AND (br.lending_status = 0 "
				+ "OR br.lending_status = 1 OR br.lending_status = 2) ORDER BY br.id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("borrowUserId", borrowUserId);
		List<BookLending> bookLendingList = template.query(strSql, param, BR_RESULT_SET_EXTRACTOR);
		return bookLendingList;
	}

	/**
	 * 貸出申請中の賃借情報一覧を取得する. 借り手側からの情報
	 * 
	 * @param borrowUserId  借り手ユーザーID
	 * @param lendingStatus 貸出状況
	 * @return 貸出情報一覧
	 */
	public List<BookLending> findByBorrowUserIdAndLendingStatus(Integer borrowUserId, Integer lendingStatus) {
		String strSql = SQL;
		strSql = strSql + " WHERE br.borrow_user_id = :borrowUserId AND br.lending_status = :lendingStatus";
		SqlParameterSource param = new MapSqlParameterSource().addValue("borrowUserId", borrowUserId)
				.addValue("lendingStatus", lendingStatus);
		List<BookLending> bookLendingList = template.query(strSql, param, BR_RESULT_SET_EXTRACTOR);
		return bookLendingList;
	}

	public List<BookLending> findByLendUserIdAndLendingStatus2(Integer lendUserId, Integer lendingStatus) {
		String sql = "SELECT lend_user_id, borrow_user_id, book_id, deadline, lendingStatus FROM book_lending WHERE lend_user_id = :lendUserId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("lend_user_id", lendUserId)
				.addValue("lendingStatus", lendingStatus);
		List<BookLending> bookLendingList = template.query(sql, param, BOOK_LENDING_ROW_MAPPER);
		return bookLendingList;
	}
}
