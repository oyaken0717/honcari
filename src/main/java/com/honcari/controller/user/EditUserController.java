package com.honcari.controller.user;

import java.sql.Timestamp;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.form.EditUserForm;
import com.honcari.service.user.EditUserService;
import com.honcari.service.user.SearchExistOtherUserByEmailService;
import com.honcari.service.user.SearchUserByUserIdService;

/**
 * ユーザー情報を編集するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@RequestMapping("/user")
public class EditUserController {
	
	@Autowired
	private EditUserService editUserService;
	
	@Autowired
	private SearchExistOtherUserByEmailService searchExistOtherUserByEmailService;
	
	@Autowired
	private SearchUserByUserIdService searchUserByUserIdService;	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@ModelAttribute
	public EditUserForm setUpEditUserForm() {
		return new EditUserForm();
	}
	
	/**
	 * プロフィール編集画面に遷移するメソッド.
	 * 
	 * @param loginUser ログイン中のユーザー
	 * @param model  リクエストスコープ
	 * @return プロフィール編集画面
	 */
	@RequestMapping("/show_edit")
	public String showEditUser(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = searchUserByUserIdService.showUser(loginUser.getUser().getUserId());
		model.addAttribute("user", user);
		return "user/edit_user";
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
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editUser(@Validated EditUserForm editUserForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser) {
		if(searchExistOtherUserByEmailService.isExistOtherUserByEmail(editUserForm)) {
			result.rejectValue("email", null, "入力されたメールアドレスは登録済のため使用できません");
		}
		//パスワードの入力があるときだけチェックを実施するためにFormでなくこちらでチェック
		String currentPassword = editUserForm.getCurrentPassword();
		String inputCurrentPassword = editUserForm.getInputCurrentPassword();
		String newPassword = editUserForm.getPassword();
		String confirmPassword = editUserForm.getConfirmPassword();
		if(!newPassword.isEmpty() 
				&& !passwordEncoder.matches(inputCurrentPassword, currentPassword)) {
			result.rejectValue("inputCurrentPassword", null, "現在のパスワードが間違っています");
		}
		if(!newPassword.isEmpty() 
				&& !newPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,32}$")) {
			result.rejectValue("password", null, "パスワードの条件を満たしていません");
		}
		if((!newPassword.isEmpty() && !newPassword.equals(confirmPassword))
				|| (!confirmPassword.isEmpty() && newPassword.isEmpty())) {
			result.rejectValue("confirmPassword", null, "パスワードが一致していません");
		}
		if(result.getErrorCount() > 1 
				|| (result.getErrorCount() == 1 && !Objects.isNull(editUserForm.getImagePath()))) {
			return showEditUser(model, loginUser);
		}
		User user = new User();
		BeanUtils.copyProperties(editUserForm, user);
		if(!newPassword.isEmpty()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			redirectAttributes.addFlashAttribute("updatePasswordMessage", "パスワードの変更が完了しました。");
		}
		if(newPassword.isEmpty()) {
			user.setPassword(currentPassword);
		}
		user.setStatus(0);
		user.setUpdatePasswordDate(new Timestamp(System.currentTimeMillis()));
		editUserService.editUser(user);
		redirectAttributes.addFlashAttribute("completeMessage", "プロフィール情報の変更が完了しました。");
		return "redirect:/user/show_mypage";
	}	
}