package com.honcari.service.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.BookRentalRepository;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * 本の返却を確認する.
 * 
 * @author shumpei
 *
 */
@Service
public class ConfirmReturnService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;

	@Autowired
	private BookRentalRepository bookRentalRepository;
	
	@Autowired
	private SendRentalMailService sendRentalMailService;

	/**
	 * 本の返却を確認する.
	 * 
	 * @param bookLendingId      本の貸出状況ID
	 * @param bookId             本ID
	 * @param updateUserName 処理ユーザー
	 */
	public void confirmReturn(Integer bookRentalId, Integer bookStatus, String updateUserName,
			Integer bookRentalVersion, Integer ownedBookInfoVersion) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		bookRental.setRentalStatus(RentalStatusEnum.RETURNED.getValue());
		bookRental.setUpdateUserName(updateUserName);
		bookRental.setVersion(bookRentalVersion);
		OwnedBookInfo ownedBookInfo = bookRental.getOwnedBookInfo();
		ownedBookInfo.setBookStatus(bookStatus);
		ownedBookInfo.setVersion(ownedBookInfoVersion);

		// データベースの更新ができなかった場合は例外処理を行う
		int updateBookRentalCount = bookRentalRepository.update(bookRental);
		int updateOwnedBookInfoCount = ownedBookInfoRepository.update(ownedBookInfo);
		if (updateBookRentalCount != 1 || updateOwnedBookInfoCount != 1) {
			throw new IllegalStateException("Faild to confirm book return!");
		}
		//メール送信
		sendRentalMailService.sendRentalMail(bookRental);
	}

}
