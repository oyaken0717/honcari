package com.honcari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.common.MyPageType;
import com.honcari.domain.LoginUser;
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
	public String showMyPage(Integer myPageType, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = 2;
		if(myPageType == null) {
			myPageType = 1;
		}
		User user = null;
		switch(MyPageType.of(myPageType)) {
		case MY_BOOK:
			user = showMyPageService.showUser(userId);
			model.addAttribute("user", user);
			break;
		case LENTAL_MANAGEMENT:
			break;
		case LENTAL_HISTORY:
			break;
		case MY_BOOK_FICTION:
			user = showMyPageService.findByCategoryId(userId, 1);
			model.addAttribute("user", user);
			break;
		case MY_BOOK_BUSINESS:
			user = showMyPageService.findByCategoryId(userId, 2);
			model.addAttribute("user", user);
			break;
		case MY_BOOK_EDUCATION:
			user = showMyPageService.findByCategoryId(userId, 3);
			model.addAttribute("user", user);
			break;
		case MY_BOOK_MAP:
			user = showMyPageService.findByCategoryId(userId, 4);
			model.addAttribute("user", user);
			break;
		case MY_BOOK_HOBBY:
			user = showMyPageService.findByCategoryId(userId, 5);
			model.addAttribute("user", user);
			break;
		case MY_BOOK_ENTERTAINMENT:
			user = showMyPageService.findByCategoryId(userId, 6);
			model.addAttribute("user", user);
			break;
		case MY_BOOK_LANGUAGE:
			user = showMyPageService.findByCategoryId(userId, 7);
			model.addAttribute("user", user);
			break;
		case MY_BOOK_CHILDREN:
			user = showMyPageService.findByCategoryId(userId, 8);
			model.addAttribute("user", user);
			break;
		case MY_BOOK_OTHER:
			user = showMyPageService.findByCategoryId(userId, 9);
			model.addAttribute("user", user);
			break;
		default:
			user = showMyPageService.showUser(loginUser.getUser().getId());
			model.addAttribute("user",user);
		}
		model.addAttribute("myPageType", myPageType);
		return "mypage";
	}
}