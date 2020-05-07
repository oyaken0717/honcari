package com.honcari.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.LoginUser;
import com.honcari.service.group.OutFromGroupService;

/**
 * グループを抜けるためのコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping(value="/group")
public class OutFromGroupController {
	
	@Autowired
	private OutFromGroupService deleteGroupRelationService;
	
	/**
	 * グループを抜けるためのメソッド.
	 * 
	 * @param id グループid
	 * @param loginUser ログインユーザー
	 * @param redirectAttributes
	 * @return グループ管理画面へ遷移
	 */
	@RequestMapping(value = "/out_group")
	public String outGroup(Integer id, @AuthenticationPrincipal LoginUser loginUser,RedirectAttributes redirectAttributes) {
		deleteGroupRelationService.outGroup(loginUser.getUser().getUserId(), id);
		
		// グループ脱退完了後の画面にてポップアップ表示するためのモデル格納
		redirectAttributes.addFlashAttribute("complete", "complete");
		redirectAttributes.addFlashAttribute("outGroup", "outGroup");
		return "redirect:/group/to_management";
	}

}
