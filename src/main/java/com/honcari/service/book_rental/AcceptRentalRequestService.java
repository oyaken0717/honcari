package com.honcari.service.book_rental;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
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
		OwnedBookInfo ownedBookInfo = bookRental.getOwnedBookInfo();

		// データベースのバージョンが更新されていた場合は例外処理を行う
		if (bookRental.getVersion() != bookRentalVersion || ownedBookInfo.getVersion() != ownedBookInfoVersion) {
			throw new OptimisticLockingFailureException("Faild to accept book rental!");
		}
		bookRental.setDeadline(bookRental.getRequestDeadline());
		bookRental.setRentalStatus(RentalStatusEnum.APPROVED.getValue());
		bookRental.setApprovalDate(new Timestamp(System.currentTimeMillis()));
		bookRental.setUpdateUserName(updateUserName);
		bookRental.setVersion(bookRentalVersion);
		ownedBookInfo.setBookStatus(BookStatusEnum.LENDING.getValue());
		ownedBookInfo.setVersion(ownedBookInfoVersion);
		int updateBookRentalCount = bookRentalRepository.update(bookRental);
		int updateOwnedBookInfoCount = ownedBookInfoRepository.update(ownedBookInfo);
		// データベースの更新ができなかった場合は例外処理を行う
		if (updateBookRentalCount != 1 || updateOwnedBookInfoCount != 1) {
			throw new IllegalStateException("Faild to accept book rental!");
		}
	}

}
