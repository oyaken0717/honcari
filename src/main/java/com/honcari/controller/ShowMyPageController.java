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
	public String showMyPage(Integer userId, Integer kindOfMyPage, Model model) {
		userId = 2;
		if(kindOfMyPage == null) {
			kindOfMyPage = 1;
		}
		switch(kindOfMyPage) {
		case 1:
			User user = showMyPageService.showUser(userId);
			model.addAttribute("user",user);
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
		model.addAttribute("kindOfMyPage", kindOfMyPage);
		return "mypage";
	}

}
