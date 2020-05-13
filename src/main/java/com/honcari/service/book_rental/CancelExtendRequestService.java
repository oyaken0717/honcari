package com.honcari.service.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private SendRentalMailService sendRentalMailService;

	public void cancelExtendRequest(Integer bookRentalId, String updateUserName, Integer bookRentalVersion) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		bookRental.setUpdateUserName(updateUserName);
		bookRental.setRentalStatus(RentalStatusEnum.APPROVED.getValue());
		bookRental.setVersion(bookRentalVersion);

		// データベースの更新ができなかった場合は例外処理を行う
		int updateCount = bookRentalRepository.update(bookRental);
		if (updateCount != 1) {
			throw new IllegalStateException("Faild to cancel extend book rental request!");
		}
		
		// メールを送信する
		bookRental.setRentalStatus(RentalStatusEnum.CANCELED.getValue());
		sendRentalMailService.sendRentalMail(bookRental);
	}

}
