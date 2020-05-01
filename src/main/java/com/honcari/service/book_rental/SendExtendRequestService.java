package com.honcari.service.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.form.ExtendRequestForm;
import com.honcari.repository.BookRentalRepository;

/**
 * 貸出延長申請を送る.
 * 
 * @author shumpei
 *
 */
@Service
@Transactional
public class SendExtendRequestService {

	@Autowired
	private BookRentalRepository bookRentalRepository;

	/**
	 * 貸出期間の延長処理を行う.
	 * 
	 * @param form           フォーム
	 * @param updateUserName 更新ユーザー名
	 */
	public void sendExtendRequest(ExtendRequestForm form, String updateUserName) {
		BookRental bookRental = bookRentalRepository.load(form.getBookRentalId());
		// データベースのバージョンが更新されていた場合は例外処理を行う
		Integer bookRentalVersion = form.getBookRentalVersion();
		if (bookRental.getVersion() != bookRentalVersion) {
			throw new OptimisticLockingFailureException("Faild to send extend request of the book!");
		}
		bookRental.setRequestDeadline(java.sql.Date.valueOf(form.getRequestDeadline()));
		bookRental.setRentalStatus(RentalStatusEnum.WAIT_EXTEND.getValue());
		bookRental.setUpdateUserName(updateUserName);
		bookRental.setVersion(bookRentalVersion);
		int updateCount = bookRentalRepository.update(bookRental);
		// データベースの更新ができなかった場合は例外処理を行う
		if (updateCount != 1) {
			throw new IllegalStateException("Faild to send extend request of the book!");
		}
	}

}
