package com.honcari.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Book;
import com.honcari.domain.BookRental;
import com.honcari.repository.BookRentalRepository;
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
	private BookRentalRepository bookLendingRepository;

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
	 * 本の貸出リクエストを送る.
	 * 
	 * @param bookId       本ID
	 * @param lendUserId   貸し手ユーザーID
	 * @param borrowUserId 借りてユーザーID
	 * @param deadline     貸出期限
	 * 
	 */
	public void runLendingBookRequest(Integer bookId, Integer lendUserId, Integer borrowUserId, Date deadline) {
		int bookLendingStatus = 0; // 貸出承認待ち
		bookLendingRepository.insert(bookId, lendUserId, borrowUserId, deadline, bookLendingStatus);
		int bookStatus = 2; // 貸出承認待ち
		bookRepository.updateStatus(bookStatus, bookId);
	}

	/**
	 * 本の貸出リクエストをキャンセルする.
	 * 
	 * @param bookLendingId 本の貸出状況ID
	 * @param bookId        本ID
	 */
	public void cancelLendingBookRequest(Integer bookLendingId, Integer bookId) {
		BookRental bookLending = new BookRental();
		bookLending.setBookLendingId(bookLendingId);
		bookLending.setLendingStatus(8); // 申請キャンセル
		bookLendingRepository.update(bookLending);
		int bookStatus = 1; // 貸出可
		bookRepository.updateStatus(bookStatus, bookId);
	}

	/**
	 * 本の貸出リクエストを承認する.
	 * 
	 * @param bookLendingId 本の貸出状況ID
	 * @param bookId 本ID
	 */
	public void runApprovalLendingBookRequest(Integer bookLendingId, Integer bookId) {
		// book_lendingテーブルの更新処理
		BookRental bookLending = new BookRental();
		bookLending.setBookLendingId(bookLendingId);
		bookLending.setLendingStatus(1); //貸出承認済み
		bookLendingRepository.update(bookLending);
		// bookテーブルの更新処理
		int bookStatus = 3; // 貸出中
		bookRepository.updateStatus(bookStatus, bookId);
	}
	
	/**
	 * 本の返却を確認する.
	 * 
	 * @param bookLendingId 本の貸出状況ID
	 * @param bookId 本ID
	 */
	public void confirmBookReturn(Integer bookLendingId, Integer bookId, Integer bookStatus) {
		BookRental bookLending = new BookRental();
		bookLending.setBookLendingId(bookLendingId);
		bookLending.setLendingStatus(3); // 返却済み
		bookLendingRepository.update(bookLending);
		bookRepository.updateStatus(bookStatus, bookId);
	}
	
	/**
	 * 貸し手から貸出状況一覧を取得する.
	 * 
	 * @param lendUserId 貸し手ID
	 * @return　貸出状況一覧
	 */
	public List<BookRental> searchBookLendingListByLendUserId(Integer lendUserId){
		return bookLendingRepository.findByLendUserIdAndLendingStatus(lendUserId);
	}
	
	/**
	 * 借り手から貸出状況一覧を取得する.
	 * 
	 * @param borrowUserId 借り手ID
	 * @return 貸出状況一覧
	 */
	public List<BookRental> searchBookLendingListByBorrowUserId(Integer borrowUserId){
		return bookLendingRepository.findByBorrowUserIdAndLendingStatus(borrowUserId);
	}
	

	/**
	 * 貸出リクエストに対し未承認の貸出情報を表示させるメソッド. （所有者側）
	 * 
	 * @param userId ユーザーID
	 * @return 貸出情報
	 */
	public List<BookRental> showWaitApprovalBookLendingList(Integer lendUserId) {
		Integer waitApprovalStatus = 0; // 承認待ち
		return bookLendingRepository.findByLendUserIdAndLendingStatus(lendUserId, waitApprovalStatus);
	}

	/**
	 * 貸出リクエストに対し未承認の貸出情報を表示させるメソッド. （申請者側）
	 * 
	 * @param userId ユーザーID
	 * @return 貸出情報
	 */
	public List<BookRental> showWaitApprovalBookBorrowingList(Integer borrowUserId) {
		Integer waitApprovalStatus = 0; // 承認待ち
		return bookLendingRepository.findByBorrowUserIdAndLendingStatus(borrowUserId, waitApprovalStatus);
	}
	
	/**
	 * 書籍情報を編集するメソッド.
	 * 
	 * @param bookId 書籍id
	 * @param categoryId カテゴリid
	 * @param comment コメント
	 */
	public void editBook(Integer bookId, Integer categoryId, String comment) {
		bookRepository.editBook(bookId, categoryId, comment);
	}
	
	/**
	 * 書籍情報を削除するメソッド.
	 * 
	 * @param bookId
	 */
	public void deleteBook(Integer bookId) {
		bookRepository.deleteBook(bookId);
	}
}