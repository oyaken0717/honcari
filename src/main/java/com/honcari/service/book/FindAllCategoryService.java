package com.honcari.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.domain.Category;
import com.honcari.repository.CategoryRepository;

/**
 * 全てのカテゴリ情報を取得するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
public class FindAllCategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * categoryテーブルの全情報を取得するメソッド.
	 * 
	 * @return カテゴリ一覧
	 */
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
}