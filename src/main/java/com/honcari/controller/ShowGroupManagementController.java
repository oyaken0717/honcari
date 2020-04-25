//package com.honcari.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.honcari.domain.Group;
//import com.honcari.domain.LoginUser;
//import com.honcari.service.ShowGroupManagementService;
//
//@Controller
//@RequestMapping(value="")
//public class ShowGroupManagementController {
//	
//	@Autowired
//	private ShowGroupManagementService showGroupListService;
//	
//	@RequestMapping(value="/to_group_management")
//	public String showBelongGroup(@AuthenticationPrincipal LoginUser loginUser,Model model) {
//		List<Group> belongGroupList = showGroupListService.showGroupListByBelongUserId(loginUser.getUser().getUserId());
//		List<Group> ownGroupList = showGroupListService.showGroupListByUserId(loginUser.getUser().getUserId());
//
//		model.addAttribute("belongGroupList",belongGroupList);
//		model.addAttribute("ownGroupList",ownGroupList);
//		return "group_management";
//	}
//
//}

//コンパイルエラーが出たため一旦コメントアウトしましたby湯口