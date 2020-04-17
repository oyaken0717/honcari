package com.honcari.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Book;
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
		int bookStatus = 2; //貸出承認待ち
		int bookLendingStatus = 0; //貸出承認待ち
		bookRepository.updateStatus(bookStatus, bookId);
		bookLendingRepository.insert(bookId, lendUserId, borrowUserId, deadline, bookLendingStatus);
	}
}
