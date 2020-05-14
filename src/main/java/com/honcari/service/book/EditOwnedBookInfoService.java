package com.honcari.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
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
	
	@Autowired
	private FindByOwnedBookInfoService findByOwnedBookInfoService;
	
	/**
	 * ユーザが所有している書籍の情報を編集するメソッド.
	 * 
	 * @param ownedBookInfo ユーザが所有している書籍の情報を変更するメソッド
	 */
	public void editOwnedBookInfo(OwnedBookInfo ownedBookInfo) {
		
		//owned_book_infoテーブル更新の直前にversionの確認を行う(不一致の場合例外処理)
		Integer nowVersion = findByOwnedBookInfoService.findByOwnedBookInfoId(ownedBookInfo.getOwnedBookInfoId()).getVersion();
		if(ownedBookInfo.getVersion() != nowVersion) {
			throw new OptimisticLockingFailureException("Failed updating status of the book!");
		}
		
		//更新処理を行う(更新が0件だった場合例外処理)
		int updateCount = ownedBookInfoRepository.update(ownedBookInfo);
		if (updateCount != 1) {
			System.out.println("kotti?");
			throw new IllegalStateException("Faild updating status of the book!");
		}
	}
}