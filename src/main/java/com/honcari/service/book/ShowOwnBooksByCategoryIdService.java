package com.honcari.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * ユーザidとカテゴリidにてユーザが所有する書籍の情報を取得するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
public class ShowMyBooksByCategoryIdService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;
	/**
	 * ユーザidとカテゴリidにてユーザが所有する書籍情報を取得するメソッド.
	 * 
	 * @param userId ユーザ情報
	 * @param categoryId カテゴリid
	 * @return ユーザidとカテゴリidに一致したユーザが所有する書籍情報
	 */
	public List<OwnedBookInfo> findByCategoryId(Integer userId, Integer categoryId, Integer page){
		return ownedBookInfoRepository.findByCategoryId(userId, categoryId, page);
	}
}
