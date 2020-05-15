package com.honcari.controller.group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.service.group.ShowGroupManagementService;

/**
 * グループ管理画面を表示するためのコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("group")
public class ShowGroupManagementController {

	@Autowired
	private ShowGroupManagementService showGroupManagementService;

	@Autowired
	private HttpSession session;

	/**
	 * グループ管理画面を表示するためのメソッド.
	 * 
	 * @param loginUser ログインユーザー
	 * @param model
	 * @return グループ管理画面へ遷移
	 */
	@RequestMapping("/to_management")
	public String showBelongGroup(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		//所属しているグループ情報が含まれるログインユーザーの情報（グループドメインから持ってくるよう修正予定）
//		User belongUser = showGroupManagementService
//				.showGroupListByBelongUserIdAndStatus(loginUser.getUser().getUserId(), 1);
//		
		//招待は来ているが承認していないグループの情報が含まれたログインユーザーの情報（グループドメインから持ってくるよう修正予定）
//		User notApprovedUser = showGroupManagementService
//				.showGroupListByBelongUserIdAndStatus(loginUser.getUser().getUserId(), 0);
		
		List<Group> belongGroupList = showGroupManagementService.showGroupListByBelongUserIdAndRelationStatus(loginUser.getUser().getUserId(), 1);
		List<Group> notApprovedGroupList = showGroupManagementService.showGroupListByBelongUserIdAndRelationStatus(loginUser.getUser().getUserId(), 0);
		
		//自身が作成したグループのリスト
		List<Group> ownGroupList = showGroupManagementService
				.showGroupListByOwnerUserId(loginUser.getUser().getUserId());
		
		model.addAttribute("belongGroupList", belongGroupList);
		model.addAttribute("notApprovedGroupList", notApprovedGroupList);
		model.addAttribute("ownGroupList", ownGroupList);
				
		return "group/group_management";
	}

}
