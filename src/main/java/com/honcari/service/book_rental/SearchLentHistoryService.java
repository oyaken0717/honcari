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
 * 貸した履歴を表示するサービス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class SearchLentHistoryService {
	
	@Autowired
	private BookRentalRepository bookLendingRepository;
	
	/**
	 * 貸した履歴のリストを取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return 貸した履歴のリスト
	 */
	public List<BookRental> showlentList(Integer userId) {
		return bookLendingRepository.findByOwnerUserIdAndRentalStatusOrderByApprovalDate(userId, RentalStatusEnum.RETURNED.getValue());
	}
	
	/**
	 * リストをページングして返すメソッド.
	 * 
	 * @param page 表示させたいページ
	 * @param size 1ページに表示させる商品数
	 * @param lentList 対象リスト
	 * @return 1ページ分のリスト
	 */
	public Page<BookRental> pagingLentList(int page, int size, List<BookRental> lentList) {
		page--;
		int startBookCount = page * size;
		List<BookRental> list;
		
		if(lentList.size() < startBookCount) {
			list = Collections.emptyList();
		}else {
			int toIndex = Math.min(startBookCount + size, lentList.size());
			list = lentList.subList(startBookCount, toIndex);
		}
		
		return new PageImpl<BookRental>(list, PageRequest.of(page, size), lentList.size());
	}

}
