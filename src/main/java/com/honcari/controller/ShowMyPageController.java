package com.honcari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.User;
import com.honcari.service.ShowMyPageService;

/**
 * マイページを表示するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@RequestMapping("")
public class ShowMyPageController {
	
	@Autowired
	private ShowMyPageService showMyPageService;
	
	/**
	 * マイページに遷移するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param model リクエストスコープ
	 * @return マイページ画面
	 */
	@RequestMapping("/show_my_page")
	public String showMyPage(Integer userId, Model model) {
		userId = 2;
		User user = showMyPageService.showUser(userId);
		model.addAttribute("user",user);
		return "mypage";
	}

}
