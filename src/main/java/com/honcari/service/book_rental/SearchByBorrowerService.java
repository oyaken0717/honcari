package com.honcari.service.book_rental;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.domain.BookRental;
import com.honcari.repository.BookRentalRepository;

/**
 * 借り手のユーザーIDから貸出情報一覧を取得する.
 * 
 * @author shumpei
 *
 */
@Service
public class SearchByBorrowerService {
	
	@Autowired
	private BookRentalRepository bookRentalRepository;
	
	/**
	 * 借り手のユーザーIDから貸出情報一覧を取得する.
	 * 
	 * @param borrowUserId 借り手ID
	 * @return 貸出状況一覧
	 */
	public List<BookRental> searchRentalListByBorrower(Integer borrowUserId){
		return bookRentalRepository.findByBorrowUserIdAndMultiRentalStatus(borrowUserId);
	}

}
