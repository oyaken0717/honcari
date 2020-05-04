package com.honcari.controller.group;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.service.group.ShowGroupDetailService;

@Controller
@RequestMapping("/group")
public class ShowGroupDetailController {
	
	@Autowired
	private ShowGroupDetailService showGroupDetailService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/show_detail")
	public String showGroupDetail(Integer id,Model model,@AuthenticationPrincipal LoginUser loginUser) {
		Group group = showGroupDetailService.showGroupDetail(id);
		
		boolean b = group.getUserList().stream().map(u -> u.getUserId()).anyMatch(u -> u==loginUser.getUser().getUserId());
		
		if(loginUser.getUser().getUserId()==group.getOwnerUserId()) {
			model.addAttribute("owner","owner");
		}
		
		model.addAttribute("b",b);
		model.addAttribute("group",group);
		model.addAttribute("userId",loginUser.getUser().getUserId());
		session.setAttribute("fromManagement", null);

		return "group/group_detail2";
	}

}
