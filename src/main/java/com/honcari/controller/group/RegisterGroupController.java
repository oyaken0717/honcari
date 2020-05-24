package com.honcari.controller.group;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.S3UploadHelper;
import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.form.RegisterGroupForm;
import com.honcari.service.group.RegisterGroupService;
import com.honcari.service.group.UpdateGroupService;

/**
 * グループ作成をするコントローラ.
 * 
 * @author yamadadai
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/group")
public class RegisterGroupController {

	@Autowired
	private RegisterGroupService registerGroupService;

	private  String Bucket_Name = System.getenv("AWS_BUCKET_NAME");
    private  String Group_Folder_Name = System.getenv("AWS_GROUP_FOLDER_NAME");

	@ModelAttribute
	public RegisterGroupForm setRegisterGroupForm() {
		return new RegisterGroupForm();
	}

	@RequestMapping("/to_register")
	public String toRegisterGroup(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		model.addAttribute("user", loginUser.getUser());
		return "group/register_group";
	}

	/**
	 * グループ作成をするメソッド.
	 * 
	 * @param form               グループ情報フォーム
	 * @param result
	 * @param redirectAttributes
	 * @return トップページ（仮）
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerGroup(@Validated RegisterGroupForm form, BindingResult result,
			RedirectAttributes redirectAttributesm, Model model, @AuthenticationPrincipal LoginUser loginUser,HttpServletRequest request) {
		if (result.hasErrors()) {
			return toRegisterGroup(model, loginUser);
		}

		List<User> userList = new ArrayList<>();
		// フォームからグループに招待するユーザー情報のリストを取得
		if (form.getUserNameList() != null) {
			form.getUserNameList().forEach(name -> {
				User user = registerGroupService.findByName(name);
				userList.add(user);
			});
		}
		
		if (!request.getHeader("REFERER").contains("heroku")) {
			Group_Folder_Name="group-test";
			Bucket_Name="honcari-image-test";
		}
		String groupImageUrl = "https://"+Bucket_Name+".s3-ap-northeast-1.amazonaws.com/"+Group_Folder_Name+"/group_default.jpg";
		Group group = new Group();
		group.setName(form.getName());
		group.setDescription(form.getDescription());
		group.setOwnerUserId(loginUser.getUser().getUserId());
		group.setGroupStatus(form.getStatus());
		group.setGroupImage(groupImageUrl);
		if(form.getStatus()==null) {
			group.setGroupStatus(0);
		}
		group = registerGroupService.insertGroup(group, userList);
	    	
		redirectAttributesm.addFlashAttribute("complete", "complete");
		redirectAttributesm.addFlashAttribute("registerGroup", "registerGroup");

		return "redirect:/group/show_detail?id=" + group.getId();
	}
}
