package com.honcari.controller.group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.service.group.SearchGroupService;

/**
 * グループ検索のためのコントローラー
 * 
 * @author yamaseki
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/group")
public class SearchGroupController {

	@Autowired
	private SearchGroupService searchGroupService;

	/**
	 * グループ検索画面へ遷移するためのメソッド.
	 * 
	 * @return グループ検索画面へ遷移
	 */
	@RequestMapping("/to_search")
	public String toSearchGroup(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		// デフォルトで全件検索
		List<Group> groupList = searchGroupService.searchGroup("", 0);

		// グループ名で曖昧検索し総データ数を取得
		Integer count = searchGroupService.count("");
		Integer totalPageNum = count / 9 + 1;
		if (count % 9 == 0)
			totalPageNum = count / 9;

		model.addAttribute("userId", loginUser.getUser().getUserId());
		model.addAttribute("groupList", groupList);
		
		//ページ数表示範囲を設定
		model.addAttribute("totalPageNum", totalPageNum);
		model.addAttribute("firstPage",1);
		model.addAttribute("lastPage",5);
		if(totalPageNum<=5) {
			model.addAttribute("firstPage",1);
			model.addAttribute("lastPage",totalPageNum);
		}
		
		model.addAttribute("name", "");
		model.addAttribute("page", 1);

		// 検索結果がなかった際のスコープ格納
		if (groupList.size() == 0) {
			model.addAttribute("groupList", null);
		}
		
		return "group/search_group";
	}

	/**
	 * グループ検索メソッド.
	 * 
	 * @param name      グループ名（曖昧検索）
	 * @param model
	 * @param loginUser ログインユーザー
	 * @return グループ検索画面へ戻る
	 */
	@RequestMapping("/search")
	public String searchGroup(String name, Model model, @AuthenticationPrincipal LoginUser loginUser, Integer page) {
		
		// グループ名で曖昧検索し総データ数を取得
		Integer count = searchGroupService.count(name);
		Integer totalPageNum = count / 9 + 1;
		if (count % 9 == 0)
			totalPageNum = count / 9;
		
		//キーワード検索およびページング直後のページ処理
		List<Group> groupList = new ArrayList<>();
		if (page == null) { //曖昧検索
			groupList = searchGroupService.searchGroup(name, 0);
			model.addAttribute("page", 1);
			if(totalPageNum<=5) {
				model.addAttribute("firstPage",1);
				model.addAttribute("lastPage",totalPageNum);
			}else {
				model.addAttribute("firstPage",1);
				model.addAttribute("lastPage",5);
			}
		} else{ //ページ遷移
			Integer offset = (page-1) * 9;
			groupList = searchGroupService.searchGroup(name, offset);
			model.addAttribute("page", page);
			if(page<=4&&totalPageNum<=5) { //指定ページが4以下で総ページ数が5以下の場合はページ範囲は1~総ページ数
				model.addAttribute("firstPage",1);
				model.addAttribute("lastPage",totalPageNum);
			}
			else if(page<=4&&totalPageNum>=5) { //指定ページが4以下で総ページ数が5以上の場合はページ範囲は1~5
				model.addAttribute("firstPage",1);
				model.addAttribute("lastPage",5);
			}
			else if(5<=page&&page==totalPageNum||page+1==totalPageNum) {
				model.addAttribute("firstPage",totalPageNum-4);
				model.addAttribute("lastPage",totalPageNum);
			}else {
				model.addAttribute("firstPage",page-2);
				model.addAttribute("lastPage",page+2);
			}
//			if(totalPageNum<=5) {
//				model.addAttribute("firstPage",1);
//				model.addAttribute("lastPage",totalPageNum);
//			}
//			if(totalPageNum>5&&page<=4) {
//				model.addAttribute("firstPage",1);
//				model.addAttribute("lastPage",5);
//			}else if(page==totalPageNum||page+1==totalPageNum) {
//				model.addAttribute("firstPage",totalPageNum-4);
//				model.addAttribute("lastPage",totalPageNum);
//			}else{
//				model.addAttribute("firstPage",page-2);
//				model.addAttribute("lastPage",page+2);
//			}
		}

		// ページング用に検索窓の入力内容と検索結果件数をスコープに格納
		model.addAttribute("name", name);
		model.addAttribute("totalPageNum", totalPageNum);
		
		//曖昧検索結果とユーザーIDをスコープにに格納
		model.addAttribute("userId", loginUser.getUser().getUserId());
		model.addAttribute("groupList", groupList);
		
		// 曖昧検索結果がなかった際のスコープ格納
		if (groupList.isEmpty())
			model.addAttribute("groupList", null);

		return "group/search_group";
	}

}
