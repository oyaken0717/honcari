package com.honcari.controller.group;

import java.util.List;

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
	public String toSearchGroup() {
		session.setAttribute("fromManagement", null);
		return "group/search_group";
	}

	/**
	 * グループ検索メソッド.
	 * 
	 * @param name グループ名（曖昧検索）
	 * @param model
	 * @param loginUser ログインユーザー
	 * @return グループ検索画面へ戻る
	 */
	@RequestMapping("/search")
	public String searchGroup(String name, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<Group> groupList = searchGroupService.searchGroup(name);

		model.addAttribute("userId", loginUser.getUser().getUserId());
		model.addAttribute("groupList", groupList);
		return "group/search_group";
	}

}
