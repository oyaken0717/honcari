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
	
	/**
	 * 本の返却を確認する.
	 * 
	 * @param bookLendingId 本の貸出状況ID
	 * @param bookId 本ID
	 * @param processingUserName 処理ユーザー
	 */
	public void confirmReturn(Integer bookRentalId, Integer bookStatus, String processingUserName) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		bookRental.setUpdateUserName(processingUserName);
		bookRental.setRentalStatus(RentalStatusEnum.RETURNED.getValue());
		bookRentalRepository.update(bookRental);
		
		OwnedBookInfo ownedBookInfo = bookRental.getOwnedBookInfo();
		ownedBookInfo.setBookStatus(bookStatus);
		ownedBookInfoRepository.update(ownedBookInfo);
	}
	

}
