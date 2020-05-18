package com.honcari.service.book_rental;

import java.sql.Date;

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
 * 貸出申請を送る.
 * 
 * @author shumpei
 *
 */
@Service
@Transactional
public class SendRentalRequestService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;

	@Autowired
	private BookRentalRepository bookRentalRepository;

	@Autowired
	private SendRentalMailService sendRentalMailService;

	/**
	 * 本の貸出申請を送る.
	 * 
	 * @param ownedBookInfoId ユーザーが所有している書籍情報ID
	 * @param borrowUserId    借り手ユーザーID
	 * @param borrowUserName 借り手ユーザー名
	 * @param requestBegining 貸出開始日
	 * @param requestDeadline 貸出期限
	 * @param ownedBookInfoVersion　ユーザーが所有している書籍情報バージョン
	 */
	public void sendRentalRequest(Integer ownedBookInfoId, Integer borrowUserId, String borrowUserName,
			Date requestBeginning, Date requestDeadline, Integer ownedBookInfoVersion) {
		BookRental bookRental = new BookRental();
		bookRental.setOwnedBookInfoId(ownedBookInfoId);
		bookRental.setBorrowUserId(borrowUserId);
		bookRental.setRentalStatus(RentalStatusEnum.WAIT_APPROVAL.getValue());
		bookRental.setRequestBeginning(requestBeginning);
		bookRental.setRequestDeadline(requestDeadline);
		bookRental.setCreationUserName(borrowUserName);
		bookRentalRepository.insert(bookRental);
		OwnedBookInfo ownedBookInfo = ownedBookInfoRepository.findByOwnedBookInfoId(ownedBookInfoId);
		ownedBookInfo.setVersion(ownedBookInfoVersion);
		ownedBookInfo.setBookStatus(BookStatusEnum.BEFORE_LENDING.getValue());

		// データベースの更新ができなかった場合は例外処理を行う
		int updateCount = ownedBookInfoRepository.update(ownedBookInfo);
		if (updateCount != 1) {
			throw new IllegalStateException("Faild updating status of the book!");
		}
		
		//メール送信を行う
		sendRentalMailService.sendRentalMail(bookRental);
	}

}
