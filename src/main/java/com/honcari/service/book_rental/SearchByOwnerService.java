package com.honcari.service.book_rental;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.domain.BookRental;
import com.honcari.repository.BookRentalRepository;

/**
 * 所有者ユーザーIDから貸出情報一覧を取得する.
 * 
 * @author shumpei
 *
 */
@Service
public class SearchByOwnerService {

	@Autowired
	private BookRentalRepository bookRentalRepository;
	
	/**
	 * 所有者ユーザーIDから貸出情報一覧を取得する.
	 * 
	 * @param ownerUserId 所有者ユーザーID
	 * @return　貸出状況一覧
	 */
	public List<BookRental> searchRentalListByOwner(Integer ownerUserId){
		return bookRentalRepository.findByOwnerUserIdAndMultiRentalStatus(ownerUserId);
	}
}
