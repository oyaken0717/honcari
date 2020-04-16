package com.honcari.repository;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * 本の貸し借り状況を操作するリポジトリ.
 * 
 * @author shumpei
 *
 */
@Repository
public class BookLendingRepository {
	
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

}
