package com.honcari.service.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
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

	/**
	 * 本の返却を確認する.
	 * 
	 * @param bookLendingId      本の貸出状況ID
	 * @param bookId             本ID
	 * @param processingUserName 処理ユーザー
	 */
	public void confirmReturn(Integer bookRentalId, Integer bookStatus, String processingUserName,
			Integer bookRentalVersion, Integer ownedBookInfoVersion) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		OwnedBookInfo ownedBookInfo = bookRental.getOwnedBookInfo();
		// データベースのバージョンが更新されていた場合は例外処理を行う
		if (bookRental.getVersion() != bookRentalVersion || ownedBookInfo.getVersion() != ownedBookInfoVersion) {
			throw new OptimisticLockingFailureException("Faild to confirm book return!");
		}
		bookRental.setUpdateUserName(processingUserName);
		bookRental.setRentalStatus(RentalStatusEnum.RETURNED.getValue());
		bookRental.setVersion(bookRentalVersion);
		ownedBookInfo.setBookStatus(bookStatus);
		ownedBookInfo.setVersion(ownedBookInfoVersion);
		int updateBookRentalCount = bookRentalRepository.update(bookRental);
		int updateOwnedBookInfoCount = ownedBookInfoRepository.update(ownedBookInfo);
		// データベースの更新ができなかった場合は例外処理を行う
		if (updateBookRentalCount != 1 || updateOwnedBookInfoCount != 1) {
			throw new IllegalStateException("Faild to confirm book return!");
		}
	}

}
