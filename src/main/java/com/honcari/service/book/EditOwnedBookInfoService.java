package com.honcari.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * ユーザが所有している書籍の情報を編集するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class EditOwnedBookInfoService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;
	
	/**
	 * ユーザが所有している書籍の情報を編集するメソッド.
	 * 
	 * @param ownedBookInfo ユーザが所有している書籍の情報を変更するメソッド
	 */
	public void editOwnedBookInfo(OwnedBookInfo ownedBookInfo) {
		ownedBookInfoRepository.editOwnedBookInfo(ownedBookInfo);
	}
}
