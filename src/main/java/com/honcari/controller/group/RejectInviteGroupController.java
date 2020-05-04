package com.honcari.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.LoginUser;
import com.honcari.service.group.RejectInviteGroupService;

@Controller
@RequestMapping("/group")
public class RejectInviteGroupController {
	
	@Autowired
	private RejectInviteGroupService rejectInviteGroupService;
	
	@RequestMapping("/reject_invite_group")
	public String rejectInviteGroup(Integer groupId,@AuthenticationPrincipal LoginUser loginUser,RedirectAttributes redirect) {
		rejectInviteGroupService.rejectInviteGroup(loginUser.getUser().getUserId(),groupId);
		redirect.addFlashAttribute("complete", "complete");
		redirect.addFlashAttribute("rejectInvite", "rejectInvite");
		return "redirect:/group/to_management";
	}

}
