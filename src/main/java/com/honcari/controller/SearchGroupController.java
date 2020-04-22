package com.honcari.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honcari.domain.Group;
import com.honcari.service.SearchGroupService;

@Controller
@RequestMapping("")
public class SearchGroupController {
	
	@Autowired
	private SearchGroupService searchGroupService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/to_search_group")
	public String toSearchGroup() {
		
		return "search_group";
	}
	
	
	@RequestMapping("/search_group")
	@ResponseBody
	public List<Group> searchGroup(String name) {
		List<Group> groupList = searchGroupService.searchGroup(name);
		
		return groupList;
	}

}
