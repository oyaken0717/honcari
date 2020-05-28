package com.honcari.controller.group;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.common.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.Group;
import com.honcari.domain.GroupRelation;
import com.honcari.domain.User;
import com.honcari.form.InviteGroupForm;
import com.honcari.service.group.InviteGroupService;
import com.honcari.service.group.RegisterGroupService;
import com.honcari.service.group.SeachInvitedUserInGroupService;
import com.honcari.service.group.SearchGroupService;

/**
 * ユーザーをグループへ招待するコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/group")
public class InviteGroupController {

	@Autowired
	private SearchGroupService searchGroupService;

	@Autowired
	private InviteGroupService inviteGroupService;

	@Autowired
	private RegisterGroupService registerGroupService;
	
	@Autowired
	private SeachInvitedUserInGroupService seachInvitedUserInGroupService;

	@ModelAttribute
	public InviteGroupForm setInviteGroupForm() {
		return new InviteGroupForm();
	}

	/**
	 * 招待画面へ遷移するためのメソッド.
	 * 
	 * @param id        グループid
	 * @param model
	 * @param loginUser ログインユーザー
	 * @return 招待画面へ遷移
	 */
	@RequestMapping("/to_invite_group")
	public String toInviteGroup(Integer id, Model model,HttpServletRequest request) {
		if(request.getHeader("REFERER")==null)return "redirect:/";
		User loginUser = (User) model.getAttribute("loginUser");
		Group group = searchGroupService.searchGroupById(id);
		
		List<GroupRelation> grList = seachInvitedUserInGroupService.seachInvitedUser(id, loginUser.getUserId());
		
		model.addAttribute("grList",grList);
		model.addAttribute("group", group);
		
		String returnParam = request.getHeader("REFERER").substring(21);
		if(request.getHeader("REFERER").contains("heroku")) {
			returnParam = request.getHeader("REFERER").substring(29);
		}
		model.addAttribute("returnParam",returnParam);
		
		return "group/invite_group";
	}

	/**
	 * 招待するためのメソッド.
	 * 
	 * @param form 招待するユーザーの名前が格納されたフォーム
	 * @param result
	 * @param redirectAttributesm
	 * @param model
	 * @param loginUser ログインユーザー
	 * @return グループ詳細画面へ遷移
	 */
	@RequestMapping(value="/invite_group",method = RequestMethod.POST)
	public String inviteGroup(@Validated InviteGroupForm form, BindingResult result,
			RedirectAttributes redirectAttributesm, Model model,HttpServletRequest request) {
		User loginUser = (User) model.getAttribute("loginUser");

		if (form.getUserNameList() == null) {
			result.rejectValue("userNameList", null, "招待したいユーザーのユーザー名を入力してください");
		}

		if (result.hasErrors()) {
			return toInviteGroup(form.getGroupId(),model,request);
		}

		// ユーザーの名前からユーザー情報を取得
		List<User> userList = new ArrayList<>();
		if (form.getUserNameList() != null) {
			form.getUserNameList().forEach(name -> {
				redirectAttributesm.addFlashAttribute("name", "joinGroup");
				User user = registerGroupService.findByName(name);
				userList.add(user);
			});
		}
		inviteGroupService.inviteGroup(loginUser.getUserId(),userList, form.getGroupId());
		redirectAttributesm.addFlashAttribute("userList", userList);

		// 招待完了後の画面にてポップアップ表示するためのモデル格納
		redirectAttributesm.addFlashAttribute("inviteGroup", "inviteGroup");
		redirectAttributesm.addFlashAttribute("complete", "complete");

		return "redirect:/group/show_detail?id=" + form.getGroupId();
	}

}
