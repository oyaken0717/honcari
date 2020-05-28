package com.honcari.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.service.group.CancelInviteService;

/**
 * グループへの招待をキャンセルするためのコントローラ.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("/group")
public class CancelInviteController {
	
	@Autowired
	private CancelInviteService cancelInviteService;
	
	/**
	 * グループへの招待をキャンセルするためのメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param groupId グループID
	 * @return グループ詳細画面へ遷移
	 */
	@RequestMapping("/cancel_invite")
	public String cancelInvite(Integer userId,Integer groupId) {
		cancelInviteService.cancelInvite(userId,groupId);
		return "redirect:/group/to_invite_group?id="+groupId;
	}

}
