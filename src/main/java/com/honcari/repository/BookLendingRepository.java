package com.honcari.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.honcari.domain.BookLending;

/**
 * 本の貸し借り状況を操作するリポジトリ.
 * 
 * @author shumpei
 *
 */
@Repository
public class BookLendingRepository {
	
	private static String sql = 
			"SELECT br.book_lending_id br.book_lending_id,br.lend_user_id br_lend_user_id,"
			+ "br.borrow_user_id br_borrow_user_id, br.book_id br_book_id, br.deadline br_deadline,"
			+ " br.lending_status br_lending_status,b.book_id, b.title book_title, b.author book_author,"
			+ "b.published_date book_published_date, b.description book_description, b.page_count book_page_count,"
			+ "b.thumbnail_path book_thumbnail_path, b.status book_status,"
			//bookLendingのlendUserIdを元にlendUserドメイを取得↓
			+ "u1.user_id u1_user_id,u1.name u1_name,u1.email "
			+ "u1_email,u1.password u1_password,u1.image_path u1_image_path,u1.profile u1_profile,b1.book_id b1_book_id,"
			+ "b1.isbn_id b1_isbn_id,b1.user_id b1_user_id, b1.category_id b1_category_id, b1.title b1_title, b1.author b1_author,"
			+ "b1.published_date b1_published_date, b1.description b1_description, b1.page_count b1_page_count, b1.thumbnail_path "
			+ "b1_thumbnail_path,b1.status b1_status, g1.group_id g1_group_id,g1.name g1_name,g1.description g1_description,"
			+ "c1.category_id c1_category_id,c1.name c1_name"
			//bookLendingのborrowUserIdを元にborrowUserドメインを取得↓
			+ "u2.user_id u2_user_id,u2.name u2_name,u2.email "
			+ "u2_email,u2.password u2_password,u2.image_path u2_image_path,u2.profile u2_profile,b2.book_id b2_book_id,"
			+ "b2.isbn_id b2_isbn_id,b2.user_id b2_user_id, b2.category_id b2_category_id, b2.title b2_title, b2.author b2_author,"
			+ "b2.published_date b2_published_date, b2.description b2_description, b2.page_count b2_page_count, b2.thumbnail_path "
			+ "b2_thumbnail_path,b2.status b2_status, g2.group_id g2_group_id,g2.name g2_name,g2.description g2_description,"
			+ "c2.category_id c2_category_id,c2.name c2_name "
			//lendUser（u1)の結合↓
			+ "FROM book_lending br LEFT OUTER JOIN books b ON br.book_id = b.book_id LEFT OUTER JOIN users u1 ON br.lend_user_id = u1.user_id "
			+ "LEFT OUTER JOIN books b1 ON u1.user_id = b1.user_id LEFT OUTER JOIN group_relationship gr1 ON u1.user_id = gr1.user_id "
			+ "LEFT OUTER JOIN groups g1 ON g1.group_id=gr1.group_i LEFT OUTER JOIN category c1 ON b1.category_id = c1.category_id "
			//borrowUser（u2)の結合↓
			+ "LEFT OUTER JOIN users u2 ON br.borrow_user_id = u2.user_id "
			+ "LEFT OUTER JOIN books b2 ON u2.user_id = b2.user_id LEFT OUTER JOIN group_relationship gr2 ON u2.user_id = gr2.user_id "
			+ "LEFT OUTER JOIN groups g2 ON g2.group_id=gr2.group_i LEFT OUTER JOIN category c2 ON b1.category_id = c2.category_id ";
	
	private static final RowMapper<BookLending> BOOK_LENDING_ROW_MAPPER = (rs, i) -> {
		BookLending bookLending = new BookLending();
		bookLending.setBookId(rs.getInt("book_lending_id"));
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
	 * @param bookId 本ID
	 * @param lendUserId 貸し手ユーザーID
	 * @param borrowUserId　借りてユーザーID
	 * @param deadline　貸出期限
	 */
	public void insert(Integer bookId, Integer lendUserId, Integer borrowUserId, Date deadline) {
		String sql = "INSERT INTO book_lending (lend_user_id, borrow_user_id, book_id, deadline) "
				+ "VALUES (:bookId, :lendUserId, :borrowUserId, :deadline)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("bookId", bookId).addValue("lendUserId", lendUserId)
										.addValue("borrowUserId", borrowUserId).addValue("deadline", deadline);
		template.update(sql, param);
	}
	
	/**
	 * 本の貸出リクエストに対し承認する.
	 * 
	 * @param bookLending 本貸出に関する情報
	 */
	public void update(BookLending bookLending) {
		String sql = "UPDATE book_lending SET lend_user_id=:lendUserId, "
				+ "borrow_user_id=:borrowUserId, book_id=:bookId, deadline=:deadLine WHERE book_id = :bookId";
		SqlParameterSource param = new BeanPropertySqlParameterSource(bookLending);
		template.update(sql, param);
	}

	public List<BookLending> findByLendUserIdAndLendingStatus(Integer lendUserId,Integer lendingStatus) {
		sql +=  " WHERE br.lend_user_id = :lendUserId OR AND br.lending_status = :lendingStatus";
		SqlParameterSource param = new MapSqlParameterSource().addValue("lend_user_id", lendUserId).addValue("lendingStatus", lendingStatus);
		List<BookLending> bookLendingList = template.query(sql, param,BOOK_LENDING_ROW_MAPPER);
		return bookLendingList;
	}
}
