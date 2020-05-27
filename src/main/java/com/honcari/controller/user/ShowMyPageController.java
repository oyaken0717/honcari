package com.honcari.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;

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

	@Autowired
	private TemplateEngine templateEngine;

	/**
	 * マイページに遷移するメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @param loginUser ログイン中のユーザー
	 * @return マイページ画面
	 */
	@RequestMapping("/show_mypage")
	public String showMyPage() {
		//キャッシュ削除
		templateEngine.clearTemplateCacheFor("user/mypage");
		
		return "user/mypage";
	}
}