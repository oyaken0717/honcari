package com.honcari.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.honcari.domain.Book;
import com.honcari.form.RegisterBookForm;
import com.honcari.service.RegisterBookService;

/**
 * 書籍情報登録に関するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@RestController
@RequestMapping(value = "/register_book_api")
public class RegisterBookAPIController {

	@Autowired
	private RegisterBookService registerBookService;

	/**
	 * 書籍登録画面でクリックされた書籍をテーブルに登録する.
	 * 
	 * @param registerBookForm 書籍登録画面でクリックされた書籍
	 */
	@ResponseBody
	@RequestMapping(value="/register_book", method=RequestMethod.POST)
	public void registerBook(RegisterBookForm registerBookForm) {
		Book book = new Book();
		book.setUserId(1); //SpringSecurity未実装の為、仮登録
		book.setCategoryId(1); //CategoryIdの取得方法未確定の為、仮登録
		book.setStatus(0); //statusの各値が未確定の為、仮登録
		BeanUtils.copyProperties(registerBookForm, book);
		registerBookService.registerBook(book);
	}
}