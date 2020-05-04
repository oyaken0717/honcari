package com.honcari.controller.group;

import javax.servlet.http.HttpSession;

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
	public String joinGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser,Model model,RedirectAttributes redirect) {
		joinGroupService.joinGroup(loginUser.getUser().getUserId(), groupId);
		if(session.getAttribute("fromManagement")!=null) {
			return "redirect:/group/to_management";
		}
		
		redirect.addFlashAttribute("joinGroup", "joinGroup");
		redirect.addFlashAttribute("complete", "complete");

		return "redirect:/group/to_management";
	}

}
