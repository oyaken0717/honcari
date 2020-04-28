package com.honcari.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Category;
import com.honcari.repository.CategoryRepository;

/**
 * 本一覧を表示させるサービスクラス.
 * 
 * @author yamadadai
 *
 */
@Service
@Transactional
public class ShowBookListService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * グループIDからカテゴリー情報を取得するメソッド
	 * 
	 * @param groupId グループID
	 * @return ユーザー情報リスト
	 */
	public List<Category> findByUserId(Integer userId) {
		return categoryRepository.findByUserId(userId);
	}
}
