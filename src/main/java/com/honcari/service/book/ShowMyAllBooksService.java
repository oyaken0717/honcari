package com.honcari.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * mybookを表示するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class ShowMyAllBooksService {

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;
	
	/**
	 * ユーザidにてユーザ情報を取得するメソッド.
	 * 
	 * @param userId ユーザid
	 * @return ユーザidに一致したユーザ情報
	 */
	public List<OwnedBookInfo> ShowMyAllBook(Integer userId, Integer page) {
		return ownedBookInfoRepository.findByUserId(userId, page);
	}
}