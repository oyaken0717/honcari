package com.honcari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Group;
import com.honcari.service.ShowGroupDetailService;

@Controller
@RequestMapping("")
public class ShowGroupDetailController {
	
	@Autowired
	private ShowGroupDetailService showGroupDetailService;
	
	@RequestMapping("/show_group_detail")
	public String showGroupDetail(Integer id,Model model) {
		Group group = showGroupDetailService.showGroupDetail(id);
		model.addAttribute("group",group);
		return "group_detail";
	}

}
