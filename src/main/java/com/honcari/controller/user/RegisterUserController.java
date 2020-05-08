package com.honcari.controller.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.honcari.form.RegisterUserForm;
import com.honcari.service.user.RegisterUserService;

/**
 * 新規登録のためのコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("/user")
public class RegisterUserController {
	
	@Autowired
	private RegisterUserService registerUserService;
	
	@ModelAttribute
	public RegisterUserForm setUpRegisterUserForm() {
		return new RegisterUserForm();
	}
	
	@RequestMapping(value="/to_register")
	public String toRegisterUser() {
		return "user/register_user";
	}
	
	/**
	 * 新規登録処理のためのメソッド.
	 * 
	 * @param form ユーザー登録情報を格納したフォーム
	 * @param result
	 * @param mode
	 * @return thank you ページへ遷移
	 */
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public String register(@Validated RegisterUserForm form, BindingResult result,Model mode) {
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			result.rejectValue("password", null, "パスワードと確認用パスワードが一致していません。");
		}

		if (registerUserService.checkUserByEmail(form.getEmail()) != null) {
			result.rejectValue("email", null, "このメールアドレスは既に登録されています。");
		}
		
		if(registerUserService.checkUserByName(form.getName()) != null) {
			result.rejectValue("name", null, "このユーザー名は既に登録されています。他のユーザー名を設定してください。");
		}

		if (result.hasErrors()) {
			return toRegisterUser();
		}

		registerUserService.registerUser(form);
		return "redirect:/user/to_thank_register";
	}
	
	@RequestMapping("/to_thank_register")
	public String toThankRegister() {
		return "user/thank_register";
	}
}
