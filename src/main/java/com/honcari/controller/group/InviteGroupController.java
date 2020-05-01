package com.honcari.controller.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.form.InviteGroupForm;
import com.honcari.service.group.InviteGroupService;
import com.honcari.service.group.RegisterGroupService;
import com.honcari.service.group.SearchGroupService;

@Controller
@RequestMapping("/group")
public class InviteGroupController {
	
	@Autowired
	private SearchGroupService searchGroupService;
	
	@Autowired
	private InviteGroupService inviteGroupService;
	
	@Autowired
	private RegisterGroupService registerGroupService;
	
	@ModelAttribute
	public InviteGroupForm setInviteGroupForm() {
		return new InviteGroupForm();
	}
	
	@RequestMapping("/to_invite_group")
	public String toInviteGroup(Integer id,Model model,@AuthenticationPrincipal LoginUser loginUser) {
		 Group group = searchGroupService.searchGroupById(id);
		 model.addAttribute("group",group);
		 model.addAttribute("user", loginUser.getUser());
		
		return "group/invite_group";
	}
	
	@RequestMapping("/invite_group")
	public String inviteGroup(@Validated InviteGroupForm form,BindingResult result, RedirectAttributes redirectAttributesm, 
			Model model,@AuthenticationPrincipal LoginUser loginUser) {
				
		if(form.getUserNameList()==null) {
			result.rejectValue("userNameList", null, "招待したいユーザーのユーザー名を入力してください");
		}
		
		if(result.hasErrors()) {
			return toInviteGroup(form.getGroupId(),model, loginUser);
		}
		
		List<User> userList = new ArrayList<>();
		if(form.getUserNameList()!=null) {
			form.getUserNameList().forEach(name -> {
				User user = registerGroupService.findByName(name);
				userList.add(user);
			});
		}
		inviteGroupService.inviteGroup(userList,form.getGroupId());
		
		return "redirect:/group/show_detail?id="+form.getGroupId();
	}

}
