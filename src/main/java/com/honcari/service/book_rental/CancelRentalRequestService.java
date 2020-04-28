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
	
	/**
	 * 本の貸出リクエストをキャンセルする.
	 * 
	 * @param bookLendingId 貸出状況ID
	 * @param processingUserName 処理ユーザー
	 */
	public void cancelRentalRequest(Integer bookRentalId, String processingUserName) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		bookRental.setUpdateUserName(processingUserName);
		bookRental.setRentalStatus(RentalStatusEnum.CANCELED.getValue());
		bookRentalRepository.update(bookRental);
		
		OwnedBookInfo ownedBookInfo = bookRental.getOwnedBookInfo();
		ownedBookInfo.setBookStatus(BookStatusEnum.RENTABLE.getValue());
		ownedBookInfoRepository.update(ownedBookInfo);
	}

}
