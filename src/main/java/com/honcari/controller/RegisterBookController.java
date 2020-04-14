package com.honcari.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RegisterBookController {

	@Autowired
	private RegisterBookService registerBookService;
	
	/**
	 * 書籍登録画面でクリックされた書籍の情報をbooksテーブルに登録する.
	 * 
	 * @param registerBookForm 書籍登録画面でクリックされた書籍情報
	 * @return 一覧画面にリダイレクト //仮
	 */
	@RequestMapping("/register_book")
	public String registerBook(RegisterBookForm registerBookForm) {
		Book book = new Book();
		book.setUserId(1); //SpringSecurity未実装の為、仮登録
		book.setCategoryId(1); //CategoryIdの取得方法未確定の為、仮登録
		book.setStatus(0); //statusの各値が未確定の為、仮登録
		BeanUtils.copyProperties(registerBookForm, book);
		registerBookService.registerBook(book);
		return "redirect:/to_top"; //本来は非同期ですが動作確認のためトップにリダイレクト
	}
	
	/**
	 * トップページにリダイレクト.
	 * 
	 * @return トップページ
	 */
	@RequestMapping("/to_top")
	public String toTop() {
		return "/";
	}
}