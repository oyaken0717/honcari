package com.honcari.service.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.common.BookStatusEnum;
import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.BookRentalRepository;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * 貸出申請を拒否する.
 * 
 * @author shumpei
 *
 */
@Service
@Transactional
public class RefuseRentalRequestService {

	@Autowired
	private BookRentalRepository bookRentalRepository;

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;
	
	@Autowired
	private SendRentalMailService sendRentalMailService;

	/**
	 * 本の貸出申請を拒否する.
	 * 
	 * @param bookRentalId         貸出情報ID
	 * @param updateUserName   処理ユーザー
	 * @param bookRentalVersion    貸出状況バージョン
	 * @param ownedBookInfoVersion 所有情報バージョン
	 */
	public void refuseRentalRequest(Integer bookRentalId, String updateUserName, Integer bookRentalVersion,
			Integer ownedBookInfoVersion) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		bookRental.setRentalStatus(RentalStatusEnum.REJECTED.getValue());
		bookRental.setUpdateUserName(updateUserName);
		bookRental.setVersion(bookRentalVersion);
		OwnedBookInfo ownedBookInfo = bookRental.getOwnedBookInfo();
		ownedBookInfo.setBookStatus(BookStatusEnum.RENTABLE.getValue());
		ownedBookInfo.setVersion(ownedBookInfoVersion);

		// データベースの更新ができなかった場合は例外処理を行う
		int updateBookRentalCount = bookRentalRepository.update(bookRental);
		int updateOwnedBookInfoCount = ownedBookInfoRepository.update(ownedBookInfo);
		if (updateBookRentalCount != 1 || updateOwnedBookInfoCount != 1) {
			throw new IllegalStateException("Faild to refuse book rental!");
		}
		
		//メール送信を行う
		sendRentalMailService.sendRentalMail(bookRental);
	}

}
