package com.honcari.controller.group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.service.group.ShowGroupManagementService;
import com.honcari.service.user.SearchUserByUserIdService;

/**
 * 他ユーザーの所属グループを表示するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/group")
public class ShowUserGroupManagementController {

	@Autowired
	private ShowGroupManagementService showGroupManagementService;

	@Autowired
	private SearchUserByUserIdService searchUserByUserIdService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 他ユーザーの所属グループ画面に遷移するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param model
	 * @return 所属グループ画面
	 */
	@RequestMapping("/show_user_group")
	public String showBelongGroup(@AuthenticationPrincipal LoginUser loginUser,Integer userId, Model model,HttpServletRequest request) {
		//所属しているグループ情報が含まれるログインユーザーの情報（グループドメインから持ってくるよう修正予定）
//		User belongUser = showGroupManagementService.showGroupListByBelongUserIdAndStatus(userId, 1);
		
		//自身が作成したグループのリスト
		List<Group> ownGroupList = showGroupManagementService.showGroupListByOwnerUserIdAndStatus(userId,0);
		
		List<Group> belongGroupList = showGroupManagementService.showGroupListByBelongUserIdAndGroupStatus(userId, 0,1);
		
//		model.addAttribute("belongUser", belongUser);
		model.addAttribute("belongGroupList", belongGroupList);
		model.addAttribute("ownGroupList", ownGroupList);
		model.addAttribute("user", searchUserByUserIdService.showUser(userId));
		
		session.setAttribute("referer", request.getHeader("REFERER"));
		return "group/user_group_management";
	}

}
