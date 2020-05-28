package com.honcari.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;

/**
 * マイページを表示するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@RequestMapping("/user")
@CommonAttribute
public class ShowMyPageController {

	/**
	 * マイページに遷移するメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @param loginUser ログイン中のユーザー
	 * @return マイページ画面
	 */
	@RequestMapping("/show_mypage")
	public String showMyPage() {
		return "user/mypage";
	}
}