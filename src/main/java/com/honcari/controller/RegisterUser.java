package com.honcari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ユーザ登録に関するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class RegisterUser {

	/**
	 * ユーザ登録画面を表示する.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("show_register_user")
	public String showTestPage() {
		return "register_user";
	}
}
