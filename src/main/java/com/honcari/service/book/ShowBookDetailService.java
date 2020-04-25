package com.honcari.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * 本詳細情報を表示するサービス.
 * 
 * @author shumpei
 *
 */
@Service
public class ShowBookDetailService {
	
	@Autowired
	private OwnedBookInfoRepository oweBookInfoRepository;
	
	/**
	 * ユーザーが所有する書籍情報を取得する.
	 * 
	 * @param ownedBookInfoId ユーザーが小遊する書籍情報ID
	 * @return　ユーザーが所有する書籍情報
	 */
	public OwnedBookInfo searchByOwnedBookId(Integer ownedBookInfoId) {
		return oweBookInfoRepository.findByOwnedBookInfoId(ownedBookInfoId);
	}

}
