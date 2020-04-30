package com.honcari.controller.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honcari.domain.User;
import com.honcari.service.group.RegisterGroupService;

/**
 * 非同期でユーザー情報を取得するコントローラ.
 * 
 * @author yamadadai
 *
 */
@RestController
@RequestMapping("")
public class SearchUserApiController {

	@Autowired
	private RegisterGroupService registerGroupService; 
	
	/**
	 * 入力された名前であいまい検索をするメソッド.
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/search_user_api")
	public Map<String, List<User>> searchUser(String name, Integer userId) {
		Map<String, List<User>> map = new HashMap<>();
		List<User> userList = registerGroupService.findByNameLike(name, userId);
		map.put("userList", userList);
		return map;
	}
	
	@RequestMapping("/search_user_api_for_invite")
	public Map<String, List<User>> searchUserNotInGroup(String name, Integer userId,Integer groupId) {
		Map<String, List<User>> map = new HashMap<>();
		List<User> userList = registerGroupService.findByNameLikeAndGroupId(name, userId, groupId);
		System.out.println(userList);
		map.put("userList", userList);
		return map;
	}
}
