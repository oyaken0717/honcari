package com.honcari.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Book;
import com.honcari.domain.BookLending;
import com.honcari.repository.BookLendingRepository;
import com.honcari.repository.BookRepository;

/**
 * 本情報に関するサービスクラス.
 * 
 * @author yamadadai
 *
 */
@Service
@Transactional
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookLendingRepository bookLendingRepository;

	/**
	 * 本IDから本情報を取得するメソッド.
	 * 
	 * @param bookId 本ID
	 * @return 本情報
	 */
	public Book findByBookId(Integer bookId) {
		return bookRepository.findByBookId(bookId);
	}
	
	/**
	 * 本の貸出リクエストを実行する.
	 * 
	 * @param bookId 本ID
	 * @param lendUserId 貸し手ユーザーID
	 * @param borrowUserId　借りてユーザーID
	 * @param deadline　貸出期限
	 * 
	 */
	public void runLendingBookRequest(Integer bookId, Integer lendUserId, Integer borrowUserId, Date deadline) {
		int status = 2; //貸出承認待ち
		bookRepository.updateStatus(status, bookId);
		bookLendingRepository.insert(bookId, lendUserId, borrowUserId, deadline);
	}
	
	
	/**
	 * 本の貸出リクエストを承認する.
	 * 
	 * @param bookId 本ID
	 */
	public void runApprovalLendingBookRequest(Integer bookId) {
		//book_lendingテーブルの更新処理
		BookLending bookLending = new BookLending();
		bookLending.setBookId(bookId);
		bookLending.setLendingStatus(1); //貸出承認済み
		bookLendingRepository.update(bookLending);
		
		//bookテーブルの更新処理
		int status = 3; //貸出中
		bookRepository.updateStatus(status, bookId);
	}
	
	/**
	 * 貸出リクエストに対し未承認の貸出情報を表示させるメソッド.
	 * 
	 * @param userId　ユーザーID
	 * @return 貸出情報
	 */
	public List<BookLending> showWaitApprovalBookLendingList(Integer userId){
		Integer waitApprovalStatus = 0; //承認待ち
		return bookLendingRepository.findByLendUserIdAndLendingStatus(userId, waitApprovalStatus);
	}
}
