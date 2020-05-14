package com.honcari.service.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.common.BookStatusEnum;
import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.BookRentalRepository;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * 貸出リクエストのキャンセルを行う.
 * 
 * @author shumpei
 *
 */
@Service
public class CancelRentalRequestService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;

	@Autowired
	private BookRentalRepository bookRentalRepository;

	@Autowired
	private SendRentalMailService sendRentalMailService;

	/**
	 * 本の貸出リクエストをキャンセルする.
	 * 
	 * @param bookLendingId        貸出状況ID
	 * @param processingUserName   処理ユーザー
	 * @param bookRentalVersion    貸出状況バージョン
	 * @param ownedBookInfoVersion 保有する本情報バージョン
	 */
	public void cancelRentalRequest(Integer bookRentalId, String updateUserName, Integer bookRentalVersion,
			Integer ownedBookInfoVersion) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		bookRental.setUpdateUserName(updateUserName);
		bookRental.setRentalStatus(RentalStatusEnum.CANCELED.getValue());
		bookRental.setVersion(bookRentalVersion);
		OwnedBookInfo ownedBookInfo = bookRental.getOwnedBookInfo();
		ownedBookInfo.setBookStatus(BookStatusEnum.RENTABLE.getValue());
		ownedBookInfo.setVersion(ownedBookInfoVersion);

		// データベースの更新ができなかった場合は例外処理を行う
		int updateBookRentalCount = bookRentalRepository.update(bookRental);
		int updateOwnedBookInfoCount = ownedBookInfoRepository.update(ownedBookInfo);
		if (updateBookRentalCount != 1 || updateOwnedBookInfoCount != 1) {
			throw new IllegalStateException("Faild to cancel book rental request!");
		}
		
		// メールを送信する
		sendRentalMailService.sendRentalMail(bookRental);
	}

}
