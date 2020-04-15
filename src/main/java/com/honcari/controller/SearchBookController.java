package com.honcari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.User;
import com.honcari.service.SearchBookService;

/**
 * あいまい検索機能のコントローラ.
 * 
 * @author yamadadai
 *
 */
@Controller
@RequestMapping("")
public class SearchBookController {

	@Autowired
	private SearchBookService userService;
	
	/**
	 * あいまい検索を実行するメソッド.
	 * 
	 * @param title 本のタイトル
	 * @param groupId グループID
	 * @param model モデル
	 * @return 本一覧画面
	 */
	@RequestMapping("/search_book")
	public String searchBook(String title, Integer groupId, Model model) {
		groupId = 1;
		List<User> userList = userService.findByGroupAndTitle(groupId, title);
		model.addAttribute("userList", userList);
		return "home";
	}
}
