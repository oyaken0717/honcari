package com.honcari.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * owned_book_infoテーブルにデータを挿入する為のサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class RegisterOwnedBookInfoService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;
	
	/**
	 * owned_book_infoテーブルにデータを挿入するメソッド.
	 * 
	 * @param ownedBookInfo 書籍情報
	 */
	public void registerOwnedBookInfo(OwnedBookInfo ownedBookInfo) {
		ownedBookInfoRepository.insertOwnedBookInfo(ownedBookInfo);
		System.out.println(ownedBookInfo);
	}
}