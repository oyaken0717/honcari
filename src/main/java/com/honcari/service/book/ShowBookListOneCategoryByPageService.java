package com.honcari.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Category;
import com.honcari.repository.CategoryRepository;

@Service
@Transactional
public class ShowBookListOneCategoryByPageService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findByUserIdAndCategoryId(Integer userId, Integer categoryId, Integer page) {
		return categoryRepository.findByUserIdAndCategoryId(userId, categoryId, page);
	}
}
