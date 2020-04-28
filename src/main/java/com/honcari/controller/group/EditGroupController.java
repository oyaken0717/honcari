package com.honcari.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Group;
import com.honcari.form.EditGroupForm;
import com.honcari.service.group.EditGroupService;
import com.honcari.service.group.ShowGroupDetailService;

@Controller
@RequestMapping("/group")
public class EditGroupController {

	@Autowired
	private ShowGroupDetailService showGroupDetailService;
	
	@Autowired
	private EditGroupService editGroupService;

	@ModelAttribute
	public EditGroupForm setUpEditGroupForm() {
		return new EditGroupForm();
	}

	@RequestMapping("/to_edit_group")
	public String toEditGroup(Integer groupId, Model model) {
		Group group = showGroupDetailService.showGroupDetail(groupId);
		model.addAttribute("group", group);
		return "group/edit_group";
	}

	@RequestMapping("/edit_group")
	public String editGroup(@Validated EditGroupForm form,BindingResult result, Model model) {
		if(result.hasErrors()) {
			return toEditGroup(form.getGroupId(), model);
		}
		editGroupService.editGroup(form);
		return "redirect:/group/show_detail?id="+form.getGroupId();
	}

}
