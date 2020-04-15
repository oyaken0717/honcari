package com.honcari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.User;
import com.honcari.service.ShowMyPageService;

@Controller
@RequestMapping("")
public class ShowMyPageController {
	
	@Autowired
	private ShowMyPageService showMyPageService;
	
	@RequestMapping("/showMyPage")
	public String showMyPage(Integer userId,Model model) {
		userId = 1;
		User user = showMyPageService.showUserInfo(userId);
		model.addAttribute("user",user);
		return "mypage";
	}

}
