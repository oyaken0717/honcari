package com.honcari.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.repository.OwnedBookInfoRepository;

/**
 * owned_book_infoテーブル内のデータ件数を取得するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class GetOwnedBookInfoCountService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;
	
	/**
	 * owned_book_infoテーブル内の全体件数を取得するメソッド.
	 * 
	 * @return userIdに一致したowned_book_infoテーブル内のデータ数
	 */
	public Integer getOwnedBookInfoCount(Integer userId) {
		return ownedBookInfoRepository.getOwnedBookInfoCount(userId);
	}
	
	/**
	 * categoryIdに一致したowned_book_infoテーブル内のデータ件数を取得するメソッド.
	 * 
	 * @param userId ユーザid
	 * @param categoryId カテゴリーid
	 * @return userId, categoryIdに一致したowned_book_infoテーブル内のデータ数
	 */
	public Integer getOwnedBookInfoCountByCategoryId(Integer userId, Integer categoryId) {
		return ownedBookInfoRepository.getOwnedBookInfoCountByCategoryId(userId, categoryId);
	}
}
