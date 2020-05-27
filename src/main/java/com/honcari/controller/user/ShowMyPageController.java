package com.honcari.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.service.user.SearchUserByUserIdService;

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
	private SearchUserByUserIdService searchUserByUserIdService;

	/**
	 * マイページに遷移するメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @param loginUser ログイン中のユーザー
	 * @return マイページ画面
	 */
	@RequestMapping("/show_mypage")
	public String showMyPage(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		
		User user = searchUserByUserIdService.showUser(loginUser.getUser().getUserId());
		model.addAttribute("user", user);
		
		return "user/mypage";
	}
}