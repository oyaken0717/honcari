package com.honcari.controller.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping(value="")
public class SearchUserApiController {

	@Autowired
	private RegisterGroupService registerGroupService; 
	
	/**
	 * 入力された名前であいまい検索をするメソッド.
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/search_user_api")
	public Map<String, List<User>> searchUser(String name, Integer userId) {
		Map<String, List<User>> map = new HashMap<>();
		List<User> userList = registerGroupService.findByNameLike(name, userId);
		map.put("userList", userList);
		return map;
	}
	
	/**
	 * 入力された名前であいまい検索をするメソッド.
	 * すでにグループに所属しているユーザーは弾く.
	 * 
	 * @param name ユーザー名
	 * @param userId ユーザーid
	 * @param groupId グループid
	 * @return ユーザー情報（map）
	 */
	@RequestMapping(value="/search_user_api_for_invite")
	public Map<String, List<User>> searchUserNotInGroup(String name, Integer userId,Integer groupId) {
		Map<String, List<User>> map = new HashMap<>();
			List<User> userList = registerGroupService.findByNameLikeAndGroupIdForInvite(name, userId, groupId);
			map.put("userList", userList);
		return map;
	}
	
	/**
	 * 入力された名前であいまい検索をするメソッド.
	 * すでにグループに所属しているユーザーのみが対象
	 * 
	 * @param name ユーザー名
	 * @param userId ユーザーid
	 * @param groupId グループid
	 * @return ユーザー情報（map）
	 */
	@RequestMapping(value="/search_user_api_for_owner_transfer")
	public Map<String, List<User>> searchUserInGroup(String name, Integer userId,Integer groupId) {
		Map<String, List<User>> map = new HashMap<>();
			List<User> userList = registerGroupService.findByNameLikeAndGroupIdForOwnerTransfer(name, userId, groupId);
			map.put("userList", userList);
		return map;
	}
	
}
