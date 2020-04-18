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

	private static String sql = "SELECT br.id br_book_lending_id,br.lend_user_id br_lend_user_id,"
			+ "br.borrow_user_id br_borrow_user_id, br.book_id br_book_id, br.deadline br_deadline,"
			+ "br.lending_status br_lending_status,b.book_id b_book_id,b.isbn_id b_isbn_id,b.user_id b_user_id,b.category_id b_category_id, b.title b_title, b.author b_author,"
			+ "b.published_date b_published_date, b.description b_description, b.page_count b_page_count,"
			+ "b.thumbnail_path b_thumbnail_path, b.status b_status,c.category_id c_category_id,c.name c_name,"
			// bookLendingのlendUserIdを元にlendUserドメイを取得↓
			+ "u1.user_id u1_user_id,u1.name u1_name,u1.email "
			+ "u1_email,u1.password u1_password,u1.image_path u1_image_path,u1.profile u1_profile,b1.book_id b1_book_id,"
			+ "b1.isbn_id b1_isbn_id,b1.user_id b1_user_id, b1.category_id b1_category_id, b1.title b1_title, b1.author b1_author,"
			+ "b1.published_date b1_published_date, b1.description b1_description, b1.page_count b1_page_count, b1.thumbnail_path "
			+ "b1_thumbnail_path,b1.status b1_status, g1.group_id g1_group_id,g1.name g1_name,g1.description g1_description,"
			+ "c1.category_id c1_category_id,c1.name c1_name,"
			// bookLendingのborrowUserIdを元にborrowUserドメインを取得↓
			+ "u2.user_id u2_user_id,u2.name u2_name,u2.email "
			+ "u2_email,u2.password u2_password,u2.image_path u2_image_path,u2.profile u2_profile,b2.book_id b2_book_id,"
			+ "b2.isbn_id b2_isbn_id,b2.user_id b2_user_id, b2.category_id b2_category_id, b2.title b2_title, b2.author b2_author,"
			+ "b2.published_date b2_published_date, b2.description b2_description, b2.page_count b2_page_count, b2.thumbnail_path "
			+ "b2_thumbnail_path,b2.status b2_status, g2.group_id g2_group_id,g2.name g2_name,g2.description g2_description,"
			+ "c2.category_id c2_category_id,c2.name c2_name "
			// bookLendingとbook（＋カテゴリー）の結合↓
			+ "FROM book_lending br LEFT OUTER JOIN books b ON br.book_id = b.book_id LEFT OUTER JOIN category c ON b.category_id = b.category_id "
			// lendUser（u1)の結合↓
			+ "LEFT OUTER JOIN users u1 ON br.lend_user_id = u1.user_id LEFT OUTER JOIN books b1 ON u1.user_id = b1.user_id "
			+ "LEFT OUTER JOIN group_relationship gr1 ON u1.user_id = gr1.user_id LEFT OUTER JOIN groups g1 ON g1.group_id=gr1.group_id "
			+ "LEFT OUTER JOIN category c1 ON b1.category_id = c1.category_id "
			// borrowUser（u2)の結合↓
			+ "LEFT OUTER JOIN users u2 ON br.borrow_user_id = u2.user_id "
			+ "LEFT OUTER JOIN books b2 ON u2.user_id = b2.user_id LEFT OUTER JOIN group_relationship gr2 ON u2.user_id = gr2.user_id "
			+ "LEFT OUTER JOIN groups g2 ON g2.group_id=gr2.group_id LEFT OUTER JOIN category c2 ON b2.category_id = c2.category_id ";

	private static ResultSetExtractor<List<BookLending>> BR_RESULT_SET_EXTRACTOR = (rs) -> {
		List<BookLending> bookLendingList = new ArrayList<>();
		User lendUser = null;
		User borrowUser = null;
		Book lenderBook = null;
		Book borrowerBook = null;
		List<Book> lenderBookList = null;
		List<Book> borrowerBookList = null;
		List<Group> lenderGroupList = null;
		List<Group> borrowerGroupList = null;
		int beforeBrId = 0;
		int beforeLenderBookId = 0;
		int beforeBorrowerBookId = 0;
		int beforeLenderGroupId = 0;
		int beforeBorrowerGroupId = 0;

		while (rs.next()) {
			int nowBrId = rs.getInt("br_book_lending_id");
			if (nowBrId != beforeBrId) {
				BookLending bookLending = new BookLending();
				lendUser = new User();
				borrowUser = new User();
				bookLending.setBookLendingId(rs.getInt("br_book_lending_id"));
				bookLending.setLendUserId(rs.getInt("br_lend_user_id"));
				bookLending.setBorrowUserId(rs.getInt("br_borrow_user_id"));
				bookLending.setBookId(rs.getInt("br_book_id"));
				bookLending.setDeadline(rs.getDate("br_deadline"));
				bookLending.setLendingStatus(rs.getInt("br_lending_status"));
				bookLending.setBookId(rs.getInt("br_book_lending_id"));
				bookLending.setLendUserId(rs.getInt("br_lend_user_id"));
				bookLendingList.add(bookLending);

				Book brBook = new Book();
				brBook.setId(rs.getInt("b_book_id"));
				brBook.setIsbnId(rs.getLong("b_isbn_id"));
				brBook.setUserId(rs.getInt("b_user_id"));
				brBook.setCategoryId(rs.getInt("b_category_id"));
				brBook.setTitle(rs.getString("b_title"));
				brBook.setAuthor(rs.getString("b_author"));
				brBook.setPublishedDate(rs.getString("b_published_date"));
				brBook.setDescription(rs.getString("b_description"));
				brBook.setPageCount(rs.getInt("b_page_count"));
				brBook.setThumbnailPath(rs.getString("b_thumbnail_path"));
				brBook.setStatus(rs.getInt("b_status"));
				Category category = new Category();
				category.setId(rs.getInt("c_category_id"));
				category.setName(rs.getString("c_name"));
				brBook.setCategory(category);
				bookLending.setBook(brBook);

				lenderBookList = new ArrayList<>();
				lenderGroupList = new ArrayList<>();
				lendUser.setId(rs.getInt("u1_user_id"));
				lendUser.setName(rs.getString("u1_name"));
				lendUser.setEmail(rs.getString("u1_email"));
				lendUser.setPassword(rs.getString("u1_password"));
				lendUser.setImagePath(rs.getString("u1_image_path"));
				lendUser.setProfile(rs.getString("u1_profile"));
				lendUser.setBookList(lenderBookList);
				lendUser.setGroupList(lenderGroupList);
				bookLending.setLendUser(lendUser);

				borrowerBookList = new ArrayList<>();
				borrowerGroupList = new ArrayList<>();
				borrowUser.setId(rs.getInt("u2_user_id"));
				borrowUser.setName(rs.getString("u2_name"));
				borrowUser.setEmail(rs.getString("u2_email"));
				borrowUser.setPassword(rs.getString("u2_password"));
				borrowUser.setImagePath(rs.getString("u2_image_path"));
				borrowUser.setProfile(rs.getString("u2_profile"));
				borrowUser.setBookList(borrowerBookList);
				borrowUser.setGroupList(borrowerGroupList);
				bookLending.setBorrowUser(borrowUser);

				beforeBrId = nowBrId;
			}

			int lenderBookId = rs.getInt("b1_book_id");
			if (lenderBookId != beforeLenderBookId) {
				lenderBook = new Book();
				lenderBook.setId(rs.getInt("b1_book_id"));
				lenderBook.setIsbnId(rs.getLong("b1_isbn_id"));
				lenderBook.setUserId(rs.getInt("b1_user_id"));
				lenderBook.setCategoryId(rs.getInt("b1_category_id"));
				lenderBook.setTitle(rs.getString("b1_title"));
				lenderBook.setAuthor(rs.getString("b1_author"));
				lenderBook.setPublishedDate(rs.getString("b1_published_date"));
				lenderBook.setDescription(rs.getString("b1_description"));
				lenderBook.setPageCount(rs.getInt("b1_page_count"));
				lenderBook.setThumbnailPath(rs.getString("b1_thumbnail_path"));
				lenderBook.setStatus(rs.getInt("b1_status"));
				Category lenderCategory = new Category();
				lenderCategory.setId(rs.getInt("c1_category_id"));
				lenderCategory.setName(rs.getString("c1_name"));
				lenderBook.setCategory(lenderCategory);
				lenderBookList.add(lenderBook);

				beforeLenderBookId = lenderBookId;
			}

			int lenderGroupId = rs.getInt("g1_group_id");
			if (lenderGroupId != beforeLenderGroupId) {
				Group lenderGroup = new Group();
				lenderGroup.setId(rs.getInt("g1_group_id"));
				lenderGroup.setName(rs.getString("g1_name"));
				lenderGroup.setDescription(rs.getString("g1_description"));
				lenderGroupList.add(lenderGroup);

				beforeLenderGroupId = lenderGroupId;
			}

			int borrowerBookId = rs.getInt("b2_book_id");
			if (borrowerBookId != beforeBorrowerBookId) {
				borrowerBook = new Book();
				borrowerBook.setId(rs.getInt("b2_book_id"));
				borrowerBook.setIsbnId(rs.getLong("b2_isbn_id"));
				borrowerBook.setUserId(rs.getInt("b2_user_id"));
				borrowerBook.setCategoryId(rs.getInt("b2_category_id"));
				borrowerBook.setTitle(rs.getString("b2_title"));
				borrowerBook.setAuthor(rs.getString("b2_author"));
				borrowerBook.setPublishedDate(rs.getString("b2_published_date"));
				borrowerBook.setDescription(rs.getString("b2_description"));
				borrowerBook.setPageCount(rs.getInt("b2_page_count"));
				borrowerBook.setThumbnailPath(rs.getString("b2_thumbnail_path"));
				borrowerBook.setStatus(rs.getInt("b2_status"));
				Category borrowerCategory = new Category();
				borrowerCategory.setId(rs.getInt("c2_category_id"));
				borrowerCategory.setName(rs.getString("c2_name"));
				borrowerBook.setCategory(borrowerCategory);
				borrowerBookList.add(borrowerBook);

				beforeBorrowerBookId = borrowerBookId;
			}

			int borrowerGroupId = rs.getInt("g2_group_id");
			if (lenderGroupId != beforeBorrowerGroupId) {
				Group borrowerGroup = new Group();
				borrowerGroup.setId(rs.getInt("g2_group_id"));
				borrowerGroup.setName(rs.getString("g2_name"));
				borrowerGroup.setDescription(rs.getString("g2_description"));
				borrowerGroupList.add(borrowerGroup);

				beforeBorrowerGroupId = borrowerGroupId;
			}

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
	 * 本の貸出リクエストに対し承認する.
	 * 
	 * @param bookLending 本貸出に関する情報
	 */
	public void update(BookLending bookLending) {
		String sql = "UPDATE book_lending SET lending_status = :lendingStatus WHERE id = :bookLendingId";
		SqlParameterSource param = new BeanPropertySqlParameterSource(bookLending);
		template.update(sql, param);
	}

	/**
	 * 貸出承認待ちの賃借情報一覧を取得する. 
	 * 貸し手（所有者）側からの情報
	 * 
	 * @param lendUserId    貸し手ユーザーID
	 * @param lendingStatus 貸出状況
	 * @return 貸借情報一覧
	 */
	public List<BookLending> findByLendUserIdAndLendingStatus(Integer lendUserId, Integer lendingStatus) {
		String strSql = sql;
		strSql = strSql + " WHERE br.lend_user_id = :lendUserId AND br.lending_status = :lendingStatus";
		SqlParameterSource param = new MapSqlParameterSource().addValue("lendUserId", lendUserId)
				.addValue("lendingStatus", lendingStatus);
		List<BookLending> bookLendingList = template.query(strSql, param, BR_RESULT_SET_EXTRACTOR);
		System.out.println(bookLendingList);
		return bookLendingList;
	}

	/**
	 * 貸出申請中の賃借情報一覧を取得する. 
	 * 借り手側からの情報
	 * 
	 * @param borrowUserId  借り手ユーザーID
	 * @param lendingStatus 貸出状況
	 * @return 貸出情報一覧
	 */
	public List<BookLending> findByBorrowUserIdAndLendingStatus(Integer borrowUserId, Integer lendingStatus) {
		String strSql = sql;
		strSql = strSql + " WHERE br.borrow_user_id = :borrowUserId AND br.lending_status = :lendingStatus";
		SqlParameterSource param = new MapSqlParameterSource().addValue("borrowUserId", borrowUserId)
				.addValue("lendingStatus", lendingStatus);
		List<BookLending> bookLendingList = template.query(strSql, param, BR_RESULT_SET_EXTRACTOR);
		System.out.println(bookLendingList);
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
