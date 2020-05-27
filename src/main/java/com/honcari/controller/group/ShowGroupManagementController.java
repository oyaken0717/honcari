package com.honcari.controller.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.service.group.SearchRequestedOwnerService;
import com.honcari.service.group.ShowGroupManagementService;

/**
 * グループ管理画面を表示するためのコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@CommonAttribute
@RequestMapping("group")
public class ShowGroupManagementController {

	@Autowired
	private ShowGroupManagementService showGroupManagementService;
	
	@Autowired
	private SearchRequestedOwnerService searchRequestedOwnerService;


	/**
	 * グループ管理画面を表示するためのメソッド.
	 * 
	 * @param loginUser ログインユーザー
	 * @param model
	 * @return グループ管理画面へ遷移
	 */
	@RequestMapping("/to_management")
	public String showBelongGroup(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		
		List<Group> belongGroupList = showGroupManagementService.showGroupListByBelongUserIdAndRelationStatus(loginUser.getUser().getUserId(), 1);
		List<Group> notApprovedGroupList = showGroupManagementService.showGroupListByBelongUserIdAndRelationStatus(loginUser.getUser().getUserId(), 0);
		
		//自身が作成したグループのリスト
		List<Group> ownGroupList = showGroupManagementService
				.showGroupListByOwnerUserId(loginUser.getUser().getUserId());
		
		List<Group> requestedOwnerGroupList = searchRequestedOwnerService.searchRequestedOwnerUser(loginUser.getUser().getUserId());
		model.addAttribute("requestedOwnerGroupList",requestedOwnerGroupList);

		model.addAttribute("belongGroupList", belongGroupList);
		model.addAttribute("notApprovedGroupList", notApprovedGroupList);
		model.addAttribute("ownGroupList", ownGroupList);
				
		return "group/group_management";
	}

}
