package com.honcari.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.common.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.User;
import com.honcari.service.user.SearchUserByUserIdService;

/**
 * ユーザーページを表示するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/user")
public class ShowUserPageController {
	
	@Autowired
	private SearchUserByUserIdService searchUserByUserIdService;

	/**
	 * ユーザーページに遷移するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param model  リクエストスコープ
	 * @return マイページ画面
	 */
	@RequestMapping("/show_user_page")
	public String showUserPage(Integer userId, Model model) {
		User user = searchUserByUserIdService.showUser(userId);
		model.addAttribute("user", user);
		return "user/user_page";
	}

}
