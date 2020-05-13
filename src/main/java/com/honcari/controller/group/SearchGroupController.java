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
		// デフォルトで全件検索
		List<Group> groupList = searchGroupService.searchGroup("", 0);

		// グループ名で曖昧検索し総データ数を取得
		Integer count = searchGroupService.count("");
		Integer totalPageNum = count / 9 + 1;
		if (count % 9 == 0)
			totalPageNum = count / 9;

		model.addAttribute("userId", loginUser.getUser().getUserId());
		model.addAttribute("groupList", groupList);
		model.addAttribute("totalPageNum", totalPageNum);
		model.addAttribute("name", "");
		model.addAttribute("page", 1);

		// 検索結果がなかった際のスコープ格納
		if (groupList.size() == 0) {
			model.addAttribute("groupList", null);
		}

		// 詳細画面からの戻るボタン用にセッション格納
//		session.setAttribute("name", "");
//		session.setAttribute("page", 0);

		// グループ管理画面からのフラグを無効化
//		session.setAttribute("fromManagement", null);
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
			model.addAttribute("page", 1);
		} else {
			Integer offset = page * 9;
			groupList = searchGroupService.searchGroup(name, offset);
			model.addAttribute("page", page + 1);
		}

		// グループ名で曖昧検索し総データ数を取得
		Integer count = searchGroupService.count(name);
		Integer totalPageNum = count / 9 + 1;
		if (count % 9 == 0)
			totalPageNum = count / 9;

		model.addAttribute("userId", loginUser.getUser().getUserId());
		model.addAttribute("groupList", groupList);

		// ページング用に検索窓の入力内容と検索結果件数をスコープに格納
		model.addAttribute("name", name);
		model.addAttribute("totalPageNum", totalPageNum);

		// 検索結果がなかった際のスコープ格納
		if (groupList.isEmpty())
			model.addAttribute("groupList", null);

		// 詳細画面からの戻るボタン用にセッション格納
//		session.setAttribute("name", name);
//		session.setAttribute("page", page);

		return "group/search_group";
	}

}
