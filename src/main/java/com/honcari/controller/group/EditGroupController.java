package com.honcari.controller.group;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.honcari.CustomControllerAdvice.CommonAttribute;
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

	@Autowired
	private HttpSession session;

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
		Group group = showGroupDetailService.showGroupDetail(groupId);
		model.addAttribute("group", group);

		String returnParam = request.getHeader("REFERER").substring(21);
		if (request.getHeader("REFERER").contains("heroku")) {
			returnParam = request.getHeader("REFERER").substring(29);
		}
		model.addAttribute("returnParam", returnParam);

		if (request.getHeader("REFERER").contains("edit_group") == true) {
			returnParam = (String) session.getAttribute("formerPage");
			if (request.getHeader("REFERER").contains("heroku")) {
				returnParam = request.getHeader("REFERER").substring(29);
			}
			model.addAttribute("returnParam", returnParam);
		}else {
			session.setAttribute("formerPage", request.getHeader("REFERER"));
		}

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
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return toEditGroup(form.getGroupId(), model, request);
		}
		if(form.getGroupStatus()==null) {
			form.setGroupStatus(0);
		}
		editGroupService.editGroup(form);
		return "redirect:/group/show_detail?id=" + form.getGroupId();
	}

}
