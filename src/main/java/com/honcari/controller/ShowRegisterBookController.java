package com.honcari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 書籍登録画面を表示する(英単語的に名前おかしいので要検討、、、.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class ShowRegisterBookController {

	/**
	 * 書籍登録画面を表示する.
	 * 
	 * @return 書籍登録画面
	 */
	@RequestMapping("/show_register_book")
	public String showRegisterBook() {
		return "/register_book";
	}
}
