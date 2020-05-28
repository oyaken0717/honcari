package com.honcari.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.GroupRelation;
import com.honcari.domain.LoginUser;
import com.honcari.service.group.JoinGroupService;
import com.honcari.service.group.SearchUserInGroupService;

/**
 * グループへ参加するためのコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("/group")
public class JoinGroupController {

	@Autowired
	private SearchUserInGroupService searchUserInGroupService;

	@Autowired
	private JoinGroupService joinGroupService;

	/**
	 * ユーザーがグループにすでに存在しているかを確認するためのメソッド.
	 * 
	 * @param groupId   グループid
	 * @param loginUser ログインユーザー
	 * @return グループ情報
	 */
	@RequestMapping("/check_user")
	@ResponseBody
	public GroupRelation checkUserInGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser) {
		GroupRelation gr = searchUserInGroupService.searchUser(loginUser.getUser().getUserId(), groupId);
		return gr;
	}

	/**
	 * グループに参加するメソッド.
	 * 
	 * @param groupId グループ情報
	 * @param loginUserログインユーザー
	 * @param model
	 * @param redirect
	 * @return グループ管理画面へ遷移
	 */
	@RequestMapping(value="/join")
	public String joinGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser, Model model,
			RedirectAttributes redirect) {
		joinGroupService.joinGroup(loginUser.getUser().getUserId(), groupId);

		// 参加完了後の画面にてポップアップ表示するためのモデル格納
		redirect.addFlashAttribute("joinGroup", "joinGroup");
		redirect.addFlashAttribute("complete", "complete");

		return "redirect:/group/to_management";
	}

}
