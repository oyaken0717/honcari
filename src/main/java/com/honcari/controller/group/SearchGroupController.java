package com.honcari.controller.group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.service.group.SearchGroupService;

/**
 * グループ検索のためのコントローラー
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("/group")
public class SearchGroupController {

	@Autowired
	private SearchGroupService searchGroupService;

	@Autowired
	private HttpSession session;

	/**
	 * グループ検索画面へ遷移するためのメソッド.
	 * 
	 * @return グループ検索画面へ遷移
	 */
	@RequestMapping("/to_search")
	public String toSearchGroup(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		List<Group> groupList = searchGroupService.searchGroup("", 0);
		Integer count = searchGroupService.count("");
		Integer totalPageNum = count / 9 + 1;
		model.addAttribute("userId", loginUser.getUser().getUserId());
		model.addAttribute("groupList", groupList);
		model.addAttribute("totalPageNum", totalPageNum);
		model.addAttribute("name", "");
		model.addAttribute("page",1);
		session.setAttribute("fromManagement", null);
		return "group/search_group";
	}

	/**
	 * グループ検索メソッド.
	 * 
	 * @param name      グループ名（曖昧検索）
	 * @param model
	 * @param loginUser ログインユーザー
	 * @return グループ検索画面へ戻る
	 */
	@RequestMapping("/search")
	public String searchGroup(String name, Model model, @AuthenticationPrincipal LoginUser loginUser, Integer page) {
		List<Group> groupList = new ArrayList<>();
		if (page == null) {
			groupList = searchGroupService.searchGroup(name, 0);
			model.addAttribute("page",1);
		} else {
			Integer offset = page * 9;
			groupList = searchGroupService.searchGroup(name, offset);
			model.addAttribute("page",page+1);
		}

		Integer count = searchGroupService.count(name);
		Integer totalPageNum = count / 9 + 1;

		model.addAttribute("userId", loginUser.getUser().getUserId());
		model.addAttribute("groupList", groupList);

		// ページング用に検索窓の入力内容と検索結果件数をスコープに格納
		model.addAttribute("name", name);
		model.addAttribute("totalPageNum", totalPageNum);
		if (groupList.isEmpty())
			model.addAttribute("groupList", null);
		return "group/search_group";
	}

}
