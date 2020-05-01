package com.honcari.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.LoginUser;
import com.honcari.service.group.OutFromGroupService;

@Controller
@RequestMapping(value="/group")
public class OutFromGroupController {
	
	@Autowired
	private OutFromGroupService deleteGroupRelationService;
	
	@RequestMapping(value="/out_group")
	public String outGroup(Integer id, @AuthenticationPrincipal LoginUser loginUser) {
		
		deleteGroupRelationService.outGroup(loginUser.getUser().getUserId(), id);
		System.out.println("グループから抜けました");
		return "redirect:/group/to_management";
	}

}
