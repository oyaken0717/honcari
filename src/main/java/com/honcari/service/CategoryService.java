package com.honcari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Category;
import com.honcari.repository.CategoryRepository;

/**
 * category情報に関するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * categoryテーブルの一覧を取得するメソッド.
	 * 
	 * @return カテゴリ一覧
	 */
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
}