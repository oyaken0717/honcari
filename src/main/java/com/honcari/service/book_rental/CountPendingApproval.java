package com.honcari.service.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.repository.BookRentalRepository;

@Service
@Transactional
public class CountPendingApproval {

	@Autowired
	private BookRentalRepository bookRentalRepository;
	
	/**
	 * 承認待ち件数を取得するメソッド.
	 * 
	 * @param loginUserId ログインユーザーid
	 * @return 承認待ち件数
	 */
	public int countPendingApproval(Integer userId) {
		return bookRentalRepository.countPendingApproval(userId);
	}
}
