package com.honcari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honcari.domain.GroupRealationship;
import com.honcari.domain.LoginUser;
import com.honcari.service.JoinGroupService;
import com.honcari.service.SearchUserInGroupService;

@Controller
@RequestMapping("")
public class JoinGroupController {
	
	@Autowired
	private SearchUserInGroupService searchUserInGroupService;
	

	
	@Autowired
	private JoinGroupService joinGroupService;
	
	@RequestMapping("/check_user_in_group")
	@ResponseBody
	public GroupRealationship checkUserInGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser) {
		GroupRealationship gr = searchUserInGroupService.searchUser(loginUser.getUser().getId(), groupId);
		return gr;
	}
	
	@RequestMapping("/join_group")
	public String joinGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser,Model model) {
		joinGroupService.joinGroup(loginUser.getUser().getId(), groupId);
		System.out.println("グループ参加成功");
		return "redirect:/to_search_group";
	}

}
