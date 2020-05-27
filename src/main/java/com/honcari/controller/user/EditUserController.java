package com.honcari.controller.user;

import java.sql.Timestamp;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

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
import org.thymeleaf.TemplateEngine;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.S3UploadHelper;
import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.form.EditUserForm;
import com.honcari.service.user.EditUserService;
import com.honcari.service.user.SearchExistOtherUserByEmailService;
import com.honcari.service.user.SearchExistOtherUserByNameService;

/**
 * ユーザー情報を編集するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/user")
public class EditUserController {
	
	@Autowired
	private EditUserService editUserService;
	
	@Autowired
	private SearchExistOtherUserByEmailService searchExistOtherUserByEmailService;
	
	@Autowired
	private SearchExistOtherUserByNameService searchExistOtherUserByNameService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private S3UploadHelper s3UploadHelper;
	
	@Autowired
	private TemplateEngine templateEngine;
	
    private String User_Folder_Name = System.getenv("AWS_USER_FOLDER_NAME");
	private String Bucket_Name = System.getenv("AWS_BUCKET_NAME");

	
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
	@RequestMapping(value="/show_edit")
	public String showEditUser(Model model, HttpServletRequest request) {
		if (!request.getHeader("REFERER").contains("heroku")) {
			User_Folder_Name = "profile-image-test";
			Bucket_Name = "honcari-image-test";
		}
		model.addAttribute("User_Folder_Name", User_Folder_Name);
		model.addAttribute("Bucket_Name", Bucket_Name);
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
			Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser,HttpServletRequest request) {
		
		if(editUserForm.getName().replaceAll("\u3000", "").equals("")) {
			result.rejectValue("name", null, "全角スペースのみのユーザー名は設定することができません");
		}
		
		if(searchExistOtherUserByNameService.isExistOtherUserByName(editUserForm)) {
			result.rejectValue("name", null, "入力された名前は登録済のため使用できません");
		}
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
				&& !newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,100}$")) {
			result.rejectValue("password", null, "パスワードの条件を満たしていません");
		}
		if((!newPassword.isEmpty() && !newPassword.equals(confirmPassword))
				|| (!confirmPassword.isEmpty() && newPassword.isEmpty())) {
			result.rejectValue("confirmPassword", null, "パスワードが一致していません");
		}
		if(editUserForm.getProfileImage().getSize()>1048576) {
			result.rejectValue("profileImage", null, "ファイルサイズが大きすぎます");
		}
		if(result.getErrorCount() >= 1 
				|| (result.getErrorCount() == 1 && !Objects.isNull(editUserForm.getProfileImage()))) {
			return showEditUser(model, request);
		}
		
		//キャッシュ削除
		templateEngine.clearTemplateCacheFor("user/mypage");
		
		User user = new User();
		BeanUtils.copyProperties(editUserForm, user);
		System.out.println("form:"+user.getPassword());
		if(!newPassword.isEmpty()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println("1:"+user.getPassword());
		}
		if(newPassword.isEmpty()) {
			user.setPassword(currentPassword);
			System.out.println("2:"+user.getPassword());
		}
		user.setStatus(0);
		user.setUpdatePasswordDate(new Timestamp(System.currentTimeMillis()));
		
		if(!"".equals(editUserForm.getProfileImage().getOriginalFilename())) {
			s3UploadHelper.saveUserFile(editUserForm.getProfileImage(), loginUser.getUser().getUserId(),request);
			if (!request.getHeader("REFERER").contains("heroku")) {
				User_Folder_Name = "profile-image-test";
				Bucket_Name = "honcari-image-test";
			}
			String groupImageUrl = "https://"+Bucket_Name+".s3-ap-northeast-1.amazonaws.com/"+User_Folder_Name+"/"+loginUser.getUser().getUserId();
			user.setImagePath(groupImageUrl);
		}else {
			user.setImagePath(editUserForm.getImagePath());
		}
		
		editUserService.editUser(user);
		if(!newPassword.isEmpty()) {
			redirectAttributes.addFlashAttribute("updatePasswordMessage", "パスワードの変更が完了しました。");
		}
		redirectAttributes.addFlashAttribute("completeMessage", "プロフィール情報の変更が完了しました。");

		return "redirect:/user/show_mypage";
	}	
}