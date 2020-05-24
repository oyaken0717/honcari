package com.honcari.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.service.group.SearchGroupService;
import com.honcari.service.group.UpdateOwnerRequestService;

@Controller
@RequestMapping("/group")
public class RequestGroupOwnerController {
	
	@Autowired
	private UpdateOwnerRequestService updateOwnerRequestService;
	
	@Autowired
	private SearchGroupService searchGroupService;
	
	@RequestMapping("/approve_owner_request")
	public String approveOwnerRequest(@AuthenticationPrincipal LoginUser loginUser,Integer groupId,RedirectAttributes redirectAttributesm) {
		Group group = searchGroupService.searchGroupById(groupId);
		group.setOwnerUserId(loginUser.getUser().getUserId());
		group.setRequestedOwnerUserId(null);
		updateOwnerRequestService.updateOwnerRequest(group);
		
		redirectAttributesm.addFlashAttribute("approveOwnerRequest", "approveOwnerRequest");
		redirectAttributesm.addFlashAttribute("complete", "complete");
		return "redirect:/group/to_management";
	}
	
	@RequestMapping("/reject_owner_request")
	public String rejectOwnerRequest(@AuthenticationPrincipal LoginUser loginUser,Integer groupId,RedirectAttributes redirectAttributesm) {
		Group group = searchGroupService.searchGroupById(groupId);
		group.setRequestedOwnerUserId(null);
		updateOwnerRequestService.updateOwnerRequest(group);
		redirectAttributesm.addFlashAttribute("rejectOwnerRequest", "rejectOwnerRequest");
		redirectAttributesm.addFlashAttribute("complete", "complete");
		return "redirect:/group/to_management";
	}

}
