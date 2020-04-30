package com.honcari.service.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.repository.BookRentalRepository;

/**
 * 貸出延長申請をキャンセルする.
 * 
 * @author shumpei
 *
 */
@Service
@Transactional
public class CancelExtendRequestService {

	@Autowired
	private BookRentalRepository bookRentalRepository;

	public void cancelExtendRequest(Integer bookRentalId, String updateUserName, Integer bookRentalVersion) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);

		// データベースのバージョンが更新されていた場合は例外処理を行う
		if (bookRental.getVersion() != bookRentalVersion) {
			throw new OptimisticLockingFailureException("Faild to cancel extend book rental request!");
		}
		bookRental.setUpdateUserName(updateUserName);
		bookRental.setRentalStatus(RentalStatusEnum.APPROVED.getValue());
		bookRental.setVersion(bookRentalVersion);
		int updateCount = bookRentalRepository.update(bookRental);
		// データベースの更新ができなかった場合は例外処理を行う
		if (updateCount != 1) {
			throw new IllegalStateException("Faild to cancel extend book rental request!");
		}

	}

}
