package com.honcari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Category;
import com.honcari.service.ShowBookListService;

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
	public String showBookList(Model model) {
		List<Category> categoryList = showBookListService.findByGroupId(3);
		model.addAttribute("categoryList", categoryList);
		return "home";
	}
}
