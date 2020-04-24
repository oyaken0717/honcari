package com.honcari.service.book_rental;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.repository.BookRentalRepository;

/**
 * レンタル履歴を表示するサービス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class ShowRentalHistoryService {
	
	@Autowired
	private BookRentalRepository bookLendingRepository;
	
	/**
	 * 借りた履歴のリストを取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return 借りた履歴のリスト
	 */
	public List<BookRental> showBorrowedList(Integer userId) {
		return bookLendingRepository.findByBorrowUserIdAndLendingStatus(userId, RentalStatusEnum.RETURNED.getValue());
	}
	
	/**
	 * 貸した履歴のリストを取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return 貸した履歴のリスト
	 */
	public List<BookRental> showlentList(Integer userId) {
		return bookLendingRepository.findByLendUserIdAndLendingStatus(userId, RentalStatusEnum.RETURNED.getValue());
	}

}
