package com.honcari.controller.group;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.Group;
import com.honcari.domain.GroupRelation;
import com.honcari.domain.LoginUser;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.service.group.SearchGroupRelationService;
import com.honcari.service.group.SearchRequestedOwnerService;
import com.honcari.service.group.ShowGroupDetailService;

/**
 * グループ詳細画面を表示するためのコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/group")
public class ShowGroupDetailController {

	@Autowired
	private ShowGroupDetailService showGroupDetailService;
	
	@Autowired
	private SearchGroupRelationService searchGroupRelationService;
	
	@Autowired
	private SearchRequestedOwnerService searchRequestedOwnerService;

	@Autowired
	private HttpSession session;
	
	@Autowired
	private TemplateEngine templateEngine;

	/**
	 * グループ詳細情報を表示するためのメソッド.
	 * 
	 * @param id        グループid
	 * @param model
	 * @param loginUser ログインユーザー
	 * @return グループ詳細画面へ遷移
	 */
	@RequestMapping("/show_detail")
	public String showGroupDetail(Integer id, Model model, @AuthenticationPrincipal LoginUser loginUser,
			HttpServletRequest request) {
		if(request.getHeader("REFERER")==null)return "redirect:/";
		
		Group group = showGroupDetailService.showGroupDetail(id);
		
		List<OwnedBookInfo>ownedBookInfoList = new ArrayList<>();
		group.getUserList().forEach(user -> {
			if(user.getOwnedBookInfoList().size() != 0) {
				user.getOwnedBookInfoList().forEach(ownedBookInfo ->{
					ownedBookInfoList.add(ownedBookInfo);
				});
			}
		});
		
		GroupRelation groupRelation = searchGroupRelationService.searchByUserIdAndGroupIdAndStatus(loginUser.getUser().getUserId(),id,0);

		// ログインユーザーがグループ内にすでに存在しているかを確認
		boolean b = group.getUserList().stream().map(u -> u.getUserId())
				.anyMatch(u -> u == loginUser.getUser().getUserId());

		// ログインユーザーがグループのオーナーか否かを確認
		if (loginUser.getUser().getUserId() == group.getOwnerUserId()) {
			model.addAttribute("owner", "owner");
		}
		
		List<Group> requestedOwnerGroupList = searchRequestedOwnerService.searchRequestedOwnerUser(loginUser.getUser().getUserId());
		if(requestedOwnerGroupList!=null) {
			requestedOwnerGroupList.forEach((i -> {
				if(i.getId()==id) {
					model.addAttribute("hasRequestOwnerAdmin","hasRequestOwnerAdmin");
				}
			}));
		}

		model.addAttribute("b", b);
		model.addAttribute("group", group);
		model.addAttribute("groupRelation",groupRelation);
		model.addAttribute("ownedBookInfoList",ownedBookInfoList);
		model.addAttribute("userId", loginUser.getUser().getUserId());
		
		//戻る機能
		String returnParam = request.getHeader("REFERER").substring(21);
		if(request.getHeader("REFERER").contains("heroku")) {
			returnParam = request.getHeader("REFERER").substring(29);
		}		
		if(request.getHeader("REFERER").contains("invite")==false&&request.getHeader("REFERER").contains("edit")==false) {
			session.setAttribute("returnParam", returnParam);
		}
		
		//キャッシュ削除
		templateEngine.clearTemplateCacheFor("group/group_detail");

		return "group/group_detail";
	}

}
