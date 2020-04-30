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
import com.honcari.domain.User;
import com.honcari.service.group.ShowGroupManagementService;

@Controller
@RequestMapping("group")
public class ShowGroupManagementController {
	
	@Autowired
	private ShowGroupManagementService showGroupManagementService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/to_management")
	public String showBelongGroup(@AuthenticationPrincipal LoginUser loginUser,Model model) {
		User belongUser = showGroupManagementService.showGroupListByBelongUserIdAndStatus(loginUser.getUser().getUserId(),1);
		User notApprovedUser = showGroupManagementService.showGroupListByBelongUserIdAndStatus(loginUser.getUser().getUserId(),0);
		List<Group> ownGroupList = showGroupManagementService.showGroupListByOwnerUserId(loginUser.getUser().getUserId());
		model.addAttribute("belongUser",belongUser);
		model.addAttribute("notApprovedUser",notApprovedUser);
		model.addAttribute("ownGroupList",ownGroupList);
		session.setAttribute("fromManagement", "fromManagement");
		return "group/group_management";
	}

}

