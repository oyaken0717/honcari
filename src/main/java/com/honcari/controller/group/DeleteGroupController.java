//package com.honcari.controller.group;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.honcari.service.group.DeleteGroupRelationService;
//import com.honcari.service.group.DeleteGroupService;
//
//@Controller
//@RequestMapping("/group")
//public class DeleteGroupController {
//	
//	@Autowired
//	private DeleteGroupService deleteGroupService;
//	
//	@Autowired
//	private DeleteGroupRelationService deleteGroupRelationService;
//	
//	@RequestMapping("/delete_group")
//	public String deleteGroup(Integer id) {
//		deleteGroupRelationService.deleteGroupByGroupId(id);
//		deleteGroupService.deleteGroup(id);
//		
//		System.out.println("グループ削除完了");
//		return "redirect:/group/to_management";
//	}
//
//}
