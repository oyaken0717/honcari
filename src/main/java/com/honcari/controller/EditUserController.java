package com.honcari.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.LoginUser;
import com.honcari.form.EditUserForm;
import com.honcari.service.EditUserService;

/**
 * ユーザー情報を編集するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
public class EditUserController {
	
	@Autowired
	private EditUserService editUserService;
	
	@Autowired
	private ShowMyPageController showMyPageController;
	
	@ModelAttribute
	public EditUserForm setUpEditUserForm() {
		return new EditUserForm();
	}
	
	/**
	 * ユーザー情報を編集するメソッド.
	 * 
	 * @param editUserForm ユーザー情報編集用フォーム
	 * @param result エラー格納オブジェクト
	 * @param model リクエストスコープ
	 * @param redirectAttributes リダイレクトスコープ
	 * @return マイページへリダイレクト
	 */
	@RequestMapping("/edit_user")
	public String editUser(@Validated EditUserForm editUserForm, BindingResult result, 
			Model model, @AuthenticationPrincipal LoginUser loginUser) {
		if(editUserService.isExistOtherUserByEmail(editUserForm)) {
			result.rejectValue("email", null, "入力されたメールアドレスは登録済のため使用できません");
		}
		if(!editUserForm.getPassword().isEmpty() 
				&& !editUserForm.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,32}$")) {
			result.rejectValue("password", null, "パスワードの条件を満たしていません");
		}
		if(!editUserForm.getPassword().isEmpty() 
				&& !(editUserForm.getPassword().equals(editUserForm.getConfirmPassword()))) {
			result.rejectValue("confirmPassword", null, "パスワードが一致していません");
		}
		if(result.getErrorCount() > 1 
				|| (result.getErrorCount() == 1 && !Objects.isNull(editUserForm.getImagePath()))) {
			return showMyPageController.showMyPage(5, model, loginUser);
		}
		editUserService.editUser(editUserForm);
		return "redirect:/to_show_my_page";
	}
	
	/**
	 * リダイレクトの際マイページを表示するために呼ばれるメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @return マイページ
	 */
	@RequestMapping("/to_show_my_page")
	public String toShowMyPage(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		return showMyPageController.showMyPage(1, model, loginUser);
	}

}
