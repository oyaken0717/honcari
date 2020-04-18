package com.honcari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.common.MyPageType;
import com.honcari.domain.User;
import com.honcari.form.EditUserForm;
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
	
	@ModelAttribute
	public EditUserForm setUpEditUserForm() {
		return new EditUserForm();
	}
	
	/**
	 * マイページに遷移するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param model リクエストスコープ
	 * @return マイページ画面
	 */
	@RequestMapping("/show_my_page")
	public String showMyPage(Integer userId, Integer myPageType, Model model) {
		userId = 2;
		if(myPageType == null) {
			myPageType = 1;
		}
		User user = null;
		switch(MyPageType.of(myPageType)) {
		case MY_BOOK:
			break;
		case LENTAL_MANAGEMENT:
			break;
		case LENTAL_HISTORY:
			break;
		default:
			user = showMyPageService.showUser(userId);
			model.addAttribute("user",user);
		}
		model.addAttribute("myPageType", myPageType);
		return "mypage";
	}

}
