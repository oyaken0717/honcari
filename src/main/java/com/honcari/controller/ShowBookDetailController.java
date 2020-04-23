package com.honcari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Book;
import com.honcari.form.LendingRequestForm;
import com.honcari.service.BookService;

/**
 * 本の詳細ページを表示するコントローラ.
 * 
 * @author shumpei
 *
 */
@RequestMapping("")
@Controller
public class ShowBookDetailController {
	
	@Autowired
	private BookService bookService;
	
	@ModelAttribute
	public LendingRequestForm setUpForm() {
		return new LendingRequestForm();
	}
	
	/**
	 * 本詳細ページを表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param bookId 本ID
	 * @return　本詳細ページ
	 */
	@RequestMapping("/show_book_detail")
	public String showBookDetail(Model model, Integer bookId) {
		Book book = bookService.findByBookId(bookId);
		model.addAttribute("book", book);
		return "book_detail";
	}
	
	
}
