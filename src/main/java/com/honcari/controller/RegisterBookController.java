package com.honcari.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Book;
import com.honcari.domain.LoginUser;
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
	 * 書籍登録画面で送られたリクエストパラメータ情報をbooksテーブルに登録する
	 * 
	 * @param registerBookForm 書籍登録画面で送られたリクエストパラメータ
	 * @param loginUser ログインしているユーザ情報
	 * @return トップ(あとで修正予定) //TODO 登録後、登録画面に戻しバナーを出す
	 */
	@RequestMapping("/register_book")
	public String registerBook(RegisterBookForm registerBookForm, @AuthenticationPrincipal LoginUser loginUser) {
		Book book = new Book();
		System.out.println(registerBookForm);
		book.setIsbnId(registerBookForm.getIsbnId());
		book.setCategoryId(Integer.parseInt(registerBookForm.getCategoryId()));
		Integer pageCount = null;
		if(registerBookForm.getPageCount().equals("undefined")) {
			pageCount = 0; //TODO値が取れなかった時の処理方法を考える
		}else {
			pageCount = Integer.parseInt(registerBookForm.getPageCount());
		}
		book.setPageCount(pageCount);
		book.setUserId(loginUser.getUser().getId());
		book.setStatus(0); //statusの各値が未確定の為、仮登録
		BeanUtils.copyProperties(registerBookForm, book);
		registerBookService.registerBook(book);
		return "redirect:/";
	}
}