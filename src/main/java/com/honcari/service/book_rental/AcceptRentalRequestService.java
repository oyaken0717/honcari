package com.honcari.service.book_rental;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.common.BookStatusEnum;
import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.BookRentalRepository;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * 貸出申請を承認する.
 * 
 * @author shumpei
 *
 */
@Service
public class AcceptRentalRequestService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;

	@Autowired
	private BookRentalRepository bookRentalRepository;
	
	@Autowired
	private SendRentalMailService sendRentalMailService;

	/**
	 * 本の貸出申請を承認する.
	 * 
	 * @param bookRentalId         貸出情報ID
	 * @param updateUserName   処理ユーザー
	 * @param bookRentalVersion    貸出状況バージョン
	 * @param ownedBookInfoVersion 所有情報バージョン
	 */
	public void acceptRentalRequest(Integer bookRentalId, String updateUserName, Integer bookRentalVersion,
			Integer ownedBookInfoVersion) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		bookRental.setBeginning(bookRental.getRequestBeginning());
		bookRental.setDeadline(bookRental.getRequestDeadline());
		bookRental.setRentalStatus(RentalStatusEnum.APPROVED.getValue());
		bookRental.setApprovalDate(new Timestamp(System.currentTimeMillis()));
		bookRental.setUpdateUserName(updateUserName);
		bookRental.setVersion(bookRentalVersion);
		OwnedBookInfo ownedBookInfo = bookRental.getOwnedBookInfo();
		ownedBookInfo.setBookStatus(BookStatusEnum.LENDING.getValue());
		ownedBookInfo.setVersion(ownedBookInfoVersion);

		// データベースの更新ができなかった場合は例外処理を行う
		int updateBookRentalCount = bookRentalRepository.update(bookRental);
		int updateOwnedBookInfoCount = ownedBookInfoRepository.update(ownedBookInfo);
		if (updateBookRentalCount != 1 || updateOwnedBookInfoCount != 1) {
			throw new IllegalStateException("Faild to accept book rental!");
		}
		//メール送信を行う
		sendRentalMailService.sendRentalMail(bookRental);
	}

}
