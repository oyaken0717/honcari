package com.honcari.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.common.BookStatusEnum;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * ユーザーが所有する本の書籍情報を削除する.
 * 
 * @author shumpei
 *
 */
@Service
public class DeleteOwnedInfoService {
	
	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;
	
	/**
	 * ユーザーが所有する本の書籍情報を削除する.
	 * 
	 * @param ownedBookInfoId ユーザーが所有する本の書籍情報ID
	 */
	public void deleteOwnedBookInfo(Integer ownedBookInfoId) {
		OwnedBookInfo ownedBookInfo = ownedBookInfoRepository.findByOwnedBookInfoId(ownedBookInfoId);
		ownedBookInfo.setBookStatus(BookStatusEnum.DELETE.getValue());
		ownedBookInfoRepository.update(ownedBookInfo);
	}
}
