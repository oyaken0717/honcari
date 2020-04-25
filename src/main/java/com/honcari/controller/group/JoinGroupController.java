package com.honcari.controller.group;

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
@RequestMapping("")
public class JoinGroupController {
	
	@Autowired
	private SearchUserInGroupService searchUserInGroupService;
	

	
	@Autowired
	private JoinGroupService joinGroupService;
	
	@RequestMapping("/check_user_in_group")
	@ResponseBody
	public GroupRelation checkUserInGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser) {
		GroupRelation gr = searchUserInGroupService.searchUser(loginUser.getUser().getUserId(), groupId);
		return gr;
	}
	
	@RequestMapping("/join_group")
	public String joinGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser,Model model) {
		joinGroupService.joinGroup(loginUser.getUser().getUserId(), groupId);
		System.out.println("グループ参加成功");
		return "redirect:/to_search_group";
	}

}
