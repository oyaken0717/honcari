package com.honcari.controller.group;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.common.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.Group;
import com.honcari.form.EditGroupForm;
import com.honcari.service.group.EditGroupService;
import com.honcari.service.group.ShowGroupDetailService;

/**
 * グループ情報を編集するコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/group")
public class EditGroupController {

	@Autowired
	private ShowGroupDetailService showGroupDetailService;

	@Autowired
	private EditGroupService editGroupService;
	
	private  String Bucket_Name = System.getenv("AWS_BUCKET_NAME");
    private  String Group_Folder_Name = System.getenv("AWS_GROUP_FOLDER_NAME");

	@ModelAttribute
	public EditGroupForm setUpEditGroupForm() {
		return new EditGroupForm();
	}

	/**
	 * グループ編集画面へ遷移するためのメソッド.
	 * 
	 * @param groupId グループid
	 * @param model
	 * @return グループ編集画面へ遷移
	 */
	@RequestMapping("/to_edit_group")
	public String toEditGroup(Integer groupId, Model model, HttpServletRequest request) {
		//URLからの直接アクセスの場合はホーム画面へリダイレクトさせる
		if(request.getHeader("REFERER")==null)return "redirect:/";
		
		Group group = showGroupDetailService.showGroupDetail(groupId);
		if(group.getRequestedOwnerUserId()!=null) {
			group.getUserList().forEach((user -> {
				if(user.getUserId()==group.getRequestedOwnerUserId()) {
					model.addAttribute("requestedOwnerUserName",user.getName());
				}
			}));
		}

		if (!request.getHeader("REFERER").contains("heroku")) {
			Group_Folder_Name="group-test";
			Bucket_Name="honcari-image-test";
		}
		
		model.addAttribute("group", group);
		model.addAttribute("Group_Folder_Name", Group_Folder_Name);
		model.addAttribute("Bucket_Name", Bucket_Name);

		return "group/edit_group";
	}

	/**
	 * グループ情報を編集するメソッド.
	 * 
	 * @param form   編集内容を格納したフォーム
	 * @param result
	 * @param model
	 * @return 編集したグループの詳細画面へ遷移
	 */
	@RequestMapping(value = "/edit_group")
	public String editGroup(@Validated EditGroupForm form, BindingResult result, Model model,
			HttpServletRequest request,RedirectAttributes redirectAttributesm) {
		
		if(form.getName().replaceAll("\u3000", "").equals("")) {
			result.rejectValue("name", null, "全角スペースのみのグループ名は設定することができません");
		}
		
		if(form.getGroupImage().getSize()>1048576) {
			result.rejectValue("profileImage", null, "ファイルサイズが大きすぎます");
		}
		
		if (result.hasErrors()) {
			return toEditGroup(form.getGroupId(), model, request);
		}

		Group group = editGroupService.editGroup(form,request);
		redirectAttributesm.addFlashAttribute("groupImageUrl", group.getGroupImage());

		return "redirect:/group/show_detail?id=" + form.getGroupId();
	}

}
