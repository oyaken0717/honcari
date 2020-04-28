package com.honcari.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Category;
import com.honcari.repository.CategoryRepository;

/**
 * ユーザー情報に関するサービスクラス.
 * 
 * @author yamadadai
 *
 */
@Service
@Transactional
public class SearchBookService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * ユーザーIDとタイトル名からカテゴリーごとの本情報を取得する.
	 * 
	 * @param userId ユーザーID
	 * @param title タイトル名
	 * @return カテゴリ一覧
	 */
	public List<Category> findByUserIdAndTitle(Integer userId, String title) {
		return categoryRepository.findByUserIdAndTitle(userId, title);
	}
}
