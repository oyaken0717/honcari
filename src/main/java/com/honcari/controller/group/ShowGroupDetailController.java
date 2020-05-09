package com.honcari.controller.group;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.service.group.ShowGroupDetailService;

/**
 * グループ詳細画面を表示するためのコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("/group")
public class ShowGroupDetailController {

	@Autowired
	private ShowGroupDetailService showGroupDetailService;

	@Autowired
	private HttpSession session;

	/**
	 * グループ詳細情報を表示するためのメソッド.
	 * 
	 * @param id グループid
	 * @param model
	 * @param loginUser ログインユーザー
	 * @return グループ詳細画面へ遷移
	 */
	@RequestMapping("/show_detail")
	public String showGroupDetail(Integer id, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Group group = showGroupDetailService.showGroupDetail(id);
		
		//ログインユーザーがグループ内にすでに存在しているかを確認
		boolean b = group.getUserList().stream().map(u -> u.getUserId())
				.anyMatch(u -> u == loginUser.getUser().getUserId());
		
		//ログインユーザーがグループのオーナーか否かを確認
		if (loginUser.getUser().getUserId() == group.getOwnerUserId()) {
			model.addAttribute("owner", "owner");
		}
		
		model.addAttribute("b", b);
		model.addAttribute("group", group);
		model.addAttribute("userId", loginUser.getUser().getUserId());
		
		//グループ管理画面から遷移してきたことを示すフラグを無効化
		session.setAttribute("fromManagement", null);

		return "group/group_detail";
	}

}
