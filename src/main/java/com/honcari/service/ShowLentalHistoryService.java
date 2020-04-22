package com.honcari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.BookLending;
import com.honcari.domain.LendingStatusEnum;
import com.honcari.repository.BookLendingRepository;

/**
 * レンタル履歴を表示するサービス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class ShowLentalHistoryService {
	
	@Autowired
	private BookLendingRepository bookLendingRepository;
	
	/**
	 * 借りた履歴のリストを取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return 借りた履歴のリスト
	 */
	public List<BookLending> showBorrowedList(Integer userId) {
		return bookLendingRepository.findByBorrowUserIdAndLendingStatus(userId, LendingStatusEnum.RETURNED.getValue());
	}
	
	/**
	 * 貸した履歴のリストを取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return 貸した履歴のリスト
	 */
	public List<BookLending> showlentList(Integer userId) {
		return bookLendingRepository.findByLendUserIdAndLendingStatus(userId, LendingStatusEnum.RETURNED.getValue());
	}

}
