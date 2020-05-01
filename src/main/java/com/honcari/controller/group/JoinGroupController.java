package com.honcari.controller.group;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honcari.domain.GroupRelation;
import com.honcari.domain.LoginUser;
import com.honcari.service.group.JoinGroupService;
import com.honcari.service.group.SearchUserInGroupService;

@Controller
@RequestMapping("/group")
public class JoinGroupController {
	
	@Autowired
	private SearchUserInGroupService searchUserInGroupService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private JoinGroupService joinGroupService;
	
	@RequestMapping("/check_user")
	@ResponseBody
	public GroupRelation checkUserInGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser) {
		GroupRelation gr = searchUserInGroupService.searchUser(loginUser.getUser().getUserId(), groupId);
		return gr;
	}
	
	@RequestMapping("/join")
	public String joinGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser,Model model) {
		joinGroupService.joinGroup(loginUser.getUser().getUserId(), groupId);
		System.out.println("グループ参加成功");
		if(session.getAttribute("fromManagement")!=null) {
			return "redirect:/group/to_management";
		}
		return "redirect:/group/to_search";
	}

}
