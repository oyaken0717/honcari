package com.honcari.controller.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * あいまい検索機能のコントローラ.
 * 
 * @author yamadadai
 *
 */
@Controller
@RequestMapping("")
public class SearchBookController {

//	@Autowired
//	private SearchBookService userService;
//	
//	/**
//	 * あいまい検索を実行するメソッド.
//	 * 
//	 * @param title 本のタイトル
//	 * @param groupId グループID
//	 * @param model モデル
//	 * @return 本一覧画面
//	 */
//	@RequestMapping("/search_book")
//	public String searchBook(String title, Integer groupId, Model model) {
//		groupId = 1;
//		List<User> userList = userService.findByGroupAndTitle(groupId, title);
//		model.addAttribute("userList", userList);
//		return "home";
//	}
}
