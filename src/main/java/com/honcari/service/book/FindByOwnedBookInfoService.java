package com.honcari.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * idからユーザが所有する書籍情報を取得するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class FindByOwnedBookInfoService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;
	
	/**
	 * IDからユーザが所有する書籍情報を取得するメソッド.
	 * 
	 * @param bookId 本ID
	 * @return 本情報
	 */
	public OwnedBookInfo findByOwnedBookInfoId(Integer ownedBookInfoId) {
		return ownedBookInfoRepository.findByOwnedBookInfoId(ownedBookInfoId);
	}
}
