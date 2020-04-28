package com.honcari.controller.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.domain.User;
import com.honcari.service.group.ShowGroupManagementService;

@Controller
@RequestMapping("group")
public class ShowGroupManagementController {
	
	@Autowired
	private ShowGroupManagementService showGroupListService;
	
	@RequestMapping("/to_management")
	public String showBelongGroup(@AuthenticationPrincipal LoginUser loginUser,Model model) {
		User user = showGroupListService.showGroupListByBelongUserId(loginUser.getUser().getUserId());
		List<Group> ownGroupList = showGroupListService.showGroupListByOwnerUserId(loginUser.getUser().getUserId());

		model.addAttribute("user",user);
		model.addAttribute("ownGroupList",ownGroupList);

		return "group/group_management";
	}

}

