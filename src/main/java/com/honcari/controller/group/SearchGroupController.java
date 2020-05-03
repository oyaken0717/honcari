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

@Controller
@RequestMapping("/group")
public class SearchGroupController {
	
	@Autowired
	private SearchGroupService searchGroupService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/to_search")
	public String toSearchGroup() {
		session.setAttribute("fromManagement", null);
		
//		return "group/search_group";
		return "group/search_group2";
	}
	
	
	@RequestMapping("/search")
	public String searchGroup(String name,Model model,@AuthenticationPrincipal LoginUser loginUser) {
		List<Group> groupList = searchGroupService.searchGroup(name);
//		boolean b = groupList.get(0).getUserList().stream().map(u -> u.getUserId()).anyMatch(u -> u==loginUser.getUser().getUserId());
//		model.addAttribute("b",b);
		model.addAttribute("userId",loginUser.getUser().getUserId());
		model.addAttribute("groupList",groupList);
		return "group/search_group2";
	}

}
