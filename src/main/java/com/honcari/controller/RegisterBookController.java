package com.honcari.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Book;
import com.honcari.form.RegisterBookForm;
import com.honcari.service.RegisterBookService;

/**
 * 書籍情報登録に関するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class RegisterBookController {

	@Autowired
	private RegisterBookService registerBookService;

	/**
	 * 書籍登録画面でクリックされた書籍をテーブルに登録する.
	 * 
	 * @param registerBookForm 書籍登録画面でクリックされた書籍
	 */
	@RequestMapping("/register_book")
	public String registerBook(RegisterBookForm registerBookForm) {
		System.out.println(registerBookForm);
		Book book = new Book();
		book.setIsbnId(Long.parseLong(registerBookForm.getIsbnId()));
		book.setCategoryId(Integer.parseInt(registerBookForm.getCategoryId()));
		book.setPageCount(Integer.parseInt(registerBookForm.getPageCount()));
		book.setUserId(1); //SpringSecurity未実装の為、仮登録
		book.setStatus(0); //statusの各値が未確定の為、仮登録
		BeanUtils.copyProperties(registerBookForm, book);
		registerBookService.registerBook(book);
		return "redirect:/";
	}
}