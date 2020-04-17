package com.honcari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Category;
import com.honcari.service.CategoryService;

/**
 * 書籍登録画面を表示する(英単語的に名前おかしいので要検討、、、.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class ShowRegisterBookController {
	
	@Autowired
	private CategoryService categoryService;

	/**
	 * 書籍登録画面を表示する.
	 * 
	 * @return 書籍登録画面
	 */
	@RequestMapping("/show_register_book")
	public String showRegisterBook(Model model) {
		List<Category> categoryList = categoryService.findAll();
		model.addAttribute("categoryList", categoryList);
		return "/register_book";
	}
}
