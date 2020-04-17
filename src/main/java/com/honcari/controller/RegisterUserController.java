package com.honcari.controller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.User;
import com.honcari.form.RegisterUserForm;
import com.honcari.service.RegisterUserService;

@Controller
@RequestMapping("")
public class RegisterUserController {
	
	@Autowired
	private RegisterUserService registerUserService;
	
	@ModelAttribute
	public RegisterUserForm setUpRegisterUserForm() {
		return new RegisterUserForm();
	}
	
	@RequestMapping("/to_register")
	public String toRegisterUser() {
		return "register_user";
	}
	
	@RequestMapping("/register")
	public String register(@Validated RegisterUserForm form, BindingResult result,Model mode) {
		System.out.println(form);
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			result.rejectValue("password", null, "パスワードと確認用パスワードが一致していません。");
		}

		if (registerUserService.checkUserByEmail(form.getEmail()) != null) {
			result.rejectValue("email", null, "このメールアドレスは既に登録されています。");
		}

		if (result.hasErrors()) {
			return toRegisterUser();
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);

		System.out.println("ここまできてるよ");
		registerUserService.registerUser(form);
		return "redirect:/toLogin";
	}
}
