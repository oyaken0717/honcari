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

	/**
	 * 本の貸出申請を送る.
	 * 
	 * @param ownedBookInfoId ユーザーが所有している書籍情報ID
	 * @param borrowUserId    借り手ユーザーID
	 * @param deadline        貸出期限
	 */
	public void sendRentalRequest(Integer ownedBookInfoId, Integer borrowUserId, String borrowUserName, Date deadline) {
		BookRental bookRental = new BookRental();
		bookRental.setOwnedBookInfoId(ownedBookInfoId);
		bookRental.setBorrowUserId(borrowUserId);
		bookRental.setCreationUserName(borrowUserName);
		bookRental.setRentalStatus(RentalStatusEnum.WAIT_APPROVAL.getValue());
		bookRental.setDeadline(deadline);
		bookRentalRepository.insert(bookRental);
		OwnedBookInfo ownedBookInfo = ownedBookInfoRepository.findByOwnedBookInfoId(ownedBookInfoId);
		ownedBookInfo.setBookStatus(BookStatusEnum.BEFORE_LENDING.getValue());
		ownedBookInfoRepository.update(ownedBookInfo);
	}

}
