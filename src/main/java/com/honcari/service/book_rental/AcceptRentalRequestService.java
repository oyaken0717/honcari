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
	 * @param bookRentalId 貸出情報ID
	 */
	public void acceptRentalRequest(Integer bookRentalId) {
		BookRental bookRental = bookRentalRepository.load(bookRentalId);
		bookRental.setRentalStatus(RentalStatusEnum.APPROVED.getValue());
		bookRentalRepository.update(bookRental);
		OwnedBookInfo ownedBookInfo = bookRental.getOwnedBookInfo();
		ownedBookInfo.setBookStatus(BookStatusEnum.LENDING.getValue());
		ownedBookInfoRepository.update(ownedBookInfo);
	}

}
