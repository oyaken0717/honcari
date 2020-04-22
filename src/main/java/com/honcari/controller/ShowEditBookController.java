package com.honcari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Book;
import com.honcari.domain.Category;
import com.honcari.service.BookService;
import com.honcari.service.CategoryService;

/**
 * 書籍登録情報を変更するページ
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class ShowEditBookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 本詳細ページを表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param bookId 本ID
	 * @return　本詳細ページ
	 */
	@RequestMapping("/show_edit_book")
	public String showEditBook(Model model, Integer bookId) {
		Book book = bookService.findByBookId(bookId);
		model.addAttribute("book", book);
		List<Category> categoryList = categoryService.findAll();
		model.addAttribute("categoryList", categoryList);
		return "edit_book";
	}
}