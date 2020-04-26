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
import com.honcari.form.RegisterGroupForm;
import com.honcari.service.group.RegisterGroupService;

/**
 * グループ作成をするコントローラ.
 * 
 * @author yamadadai
 *
 */
@Controller
@RequestMapping("")
public class RegisterGroupController {

	@Autowired
	private RegisterGroupService registerGroupService;
	
	@ModelAttribute
	public RegisterGroupForm setRegisterGroupForm() {
		return new RegisterGroupForm();
	}
	
	@RequestMapping("/to_register_group")
	public String toRegisterGroup(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		model.addAttribute("user", loginUser.getUser());
		return "group/register_group";
	}
	
	/**
	 * グループ作成をするメソッド.
	 * 
	 * @param form グループ情報フォーム
	 * @param result 
	 * @param redirectAttributes
	 * @return トップページ（仮）
	 */
	@RequestMapping("/register_group")
	public String registerGroup(@Validated RegisterGroupForm form, BindingResult result, RedirectAttributes redirectAttributesm, 
			Model model, @AuthenticationPrincipal LoginUser loginUser) {
		if(result.hasErrors()) {
			return toRegisterGroup(model, loginUser);
		}
		
		List<User> userList = new ArrayList<>();
		//フォームからユーザー情報リストを取得
		form.getUserNameList().forEach(name -> {
			User user = registerGroupService.findByName(name);
			userList.add(user);
		});
		
		Group group = new Group();
		group.setName(form.getName());
		group.setDescription(form.getDescription());
		group.setUserId(loginUser.getUser().getUserId());
		registerGroupService.insertGroup(group, userList);
		return "book/home";
	}
}
