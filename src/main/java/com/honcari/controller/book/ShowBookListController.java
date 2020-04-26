package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Category;
import com.honcari.domain.LoginUser;
import com.honcari.service.book.ShowBookListService;

/**
 * 本一覧を表示するコントローラ.
 * 
 * @author yamadadai
 *
 */
@Controller
@RequestMapping("")
public class ShowBookListController {

	@Autowired
	private ShowBookListService showBookListService;
	
	/**
	 * 本一覧を表示するメソッド.
	 * 
	 * @param model モデル
	 * @return 本一覧画面
	 */
	@RequestMapping("/")
	public String showBookList(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<Category> categoryList = showBookListService.findByUserId(loginUser.getUser().getUserId());
		model.addAttribute("categoryList", categoryList);
		return "book/home";
	}
}
