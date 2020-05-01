package com.honcari.service.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.repository.BookRentalRepository;

/**
 * 貸出延長申請を拒否する.
 * 
 * @author shumpei
 *
 */
@Service
@Transactional
public class RefuseExtendRequestService {

	@Autowired
	private BookRentalRepository bookRentalRepository;

	/**
	 * 貸出延長申請を拒否する.
	 * 
	 * @param bookRentalId      貸出情報ID
	 * @param updateUserName    処理ユーザー
	 * @param bookRentalVersion 貸出状況バージョン
	 */
	public void refuseExtendRequest(Integer bookRentalId, String updateUserName, Integer bookRentalVersion) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		// データベースのバージョンが更新されていた場合は例外処理を行う
		if (bookRental.getVersion() != bookRentalVersion) {
			throw new OptimisticLockingFailureException("Faild to refuse book rental!");
		}
		bookRental.setRentalStatus(RentalStatusEnum.APPROVED.getValue());
		bookRental.setUpdateUserName(updateUserName);
		bookRental.setVersion(bookRentalVersion);
		int updateCount = bookRentalRepository.update(bookRental);
		// データベースの更新ができなかった場合は例外処理を行う
		if (updateCount != 1) {
			throw new IllegalStateException("Faild to refuse book rental!");
		}
	}
}
