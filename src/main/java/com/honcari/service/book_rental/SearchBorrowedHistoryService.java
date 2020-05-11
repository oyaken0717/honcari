package com.honcari.service.book_rental;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.repository.BookRentalRepository;

/**
 * 借りた履歴を表示するサービス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class SearchBorrowedHistoryService {
	
	@Autowired
	private BookRentalRepository bookLendingRepository;
	
	/**
	 * 借りた履歴のリストを取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return 借りた履歴のリスト
	 */
	public List<BookRental> showBorrowedList(Integer userId) {
		return bookLendingRepository.findByBorrowUserIdAndRentalStatusOrderByApprovalDate(userId, RentalStatusEnum.RETURNED.getValue());
	}
	
	/**
	 * リストをページングして返すメソッド.
	 * 
	 * @param page 表示させたいページ
	 * @param size 1ページに表示させる商品数
	 * @param borrowedList 対象リスト
	 * @return 1ページ分のリスト
	 */
	public Page<BookRental> pagingBorrowedList(int page, int size, List<BookRental> borrowedList){
		page--;
		int startBookCount = page * size;
		List<BookRental> list;
		
		if(borrowedList.size() < startBookCount) {
			list = Collections.emptyList();
		}else {
			int toIndex = Math.min(startBookCount + size, borrowedList.size());
			list = borrowedList.subList(startBookCount, toIndex);
		}
		
		return new PageImpl<BookRental>(list, PageRequest.of(page, size), borrowedList.size());
	}
	
}
