package com.honcari.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.LoginUser;
import com.honcari.service.group.RejectInviteGroupService;

/**
 * グループ招待に対し、拒否するためのコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("/group")
public class RejectInviteGroupController {

	@Autowired
	private RejectInviteGroupService rejectInviteGroupService;

	/**
	 * グループ招待に対し、拒否するためのメソッド.
	 * 
	 * @param groupId
	 * @param loginUser
	 * @param redirect
	 * @return
	 */
	@RequestMapping(value="/reject_invite_group")
	public String rejectInviteGroup(Integer groupId, @AuthenticationPrincipal LoginUser loginUser,
			RedirectAttributes redirect) {
		rejectInviteGroupService.rejectInviteGroup(loginUser.getUser().getUserId(), groupId);
		
		// 招待拒否完了をポップアップ表示するためのモデル格納
		redirect.addFlashAttribute("complete", "complete");
		redirect.addFlashAttribute("rejectInvite", "rejectInvite");
		return "redirect:/group/to_management";
	}

}
