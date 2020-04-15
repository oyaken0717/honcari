package com.honcari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Book;
import com.honcari.service.BookService;

/**
 * 本一覧を表示するコントローラ.
 * 
 * @author yamadadai
 *
 */
@Controller
@RequestMapping("")
public class ShowBookListController {

	@Autowired
	private BookService bookService;
	
	/**
	 * 本一覧を表示するメソッド.
	 * 
	 * @param model モデル
	 * @return 本一覧画面
	 */
	@RequestMapping("/")
	public String showBookList(Model model) {
		List<Book> bookList = bookService.findByGroupId(1);
		model.addAttribute("bookList", bookList);
		return "home";
	}
}
