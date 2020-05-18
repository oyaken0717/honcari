package com.honcari.service.book_rental;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.domain.BookRental;
import com.honcari.repository.BookRentalRepository;

/**
 * ユーザーの所有する本情報からレンタル情報を取得する.
 * 
 * @author shumpei
 *
 */
@Service
public class SearchByOwnedBookInfoService {

	@Autowired
	private BookRentalRepository bookRentalRepository;

	/**
	 * ユーザーの所有する本情報からレンタル情報を取得する.
	 * 
	 * @param ownedBookInfoId ユーザーの所有する本情報ID
	 * @return レンタル情報一覧
	 */
	public BookRental searchByOwnedBookInfo(Integer ownedBookInfoId) {
		List<BookRental> bookRentalList = bookRentalRepository.findByOwnedBookInfoId(ownedBookInfoId);
		if (bookRentalList.size() != 0) {
			return bookRentalList.get(0);
		}
		return null;
	}

}
