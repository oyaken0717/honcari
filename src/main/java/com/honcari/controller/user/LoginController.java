package com.honcari.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ログイン画面へ遷移するためのコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("/user")
public class LoginController {
	
	/**
	 * ログイン画面へ遷移するためのメソッド.
	 * 
	 * @param model
	 * @param error
	 * @return ログイン画面へ遷移（SpringSecurity)
	 */
	@RequestMapping(value="/to_login")
	public String toLogin(Model model,@RequestParam(required = false) String error) {
		if (error != null) {
			System.err.println("login failed");
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
		}
		return "user/login";
	}

}
