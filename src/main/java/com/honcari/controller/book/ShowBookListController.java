package com.honcari.controller.book;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.common.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.Category;
import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.service.book.ShowBookListOneCategory;
import com.honcari.service.book.ShowBookListOneCategoryByPageService;
import com.honcari.service.book.ShowBookListService;
import com.honcari.service.book_rental.CountPendingApproval;
import com.honcari.service.group.ShowGroupManagementService;

/**
 * 本一覧を表示するコントローラ.
 * 
 * @author yamadadai
 *
 */
@Controller
@RequestMapping("/")
@CommonAttribute
public class ShowBookListController {

	@Autowired
	private ShowBookListService showBookListService;
	
	@Autowired
	private ShowBookListOneCategory showBookListOneCategory;
	
	@Autowired 
	private ShowBookListOneCategoryByPageService ShowBookListOneCategoryByPageService;
	
	@Autowired
	private CountPendingApproval countPendingApproval;
	
	@Autowired
	private ShowGroupManagementService showGroupManagementService;
	
	/**
	 * 本一覧を表示するメソッド.
	 * 
	 * @param model モデル
	 * @return 本一覧画面
	 */
	@RequestMapping("")
	public String showBookList(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = loginUser.getUser().getUserId();
		List<Category> categoryList = showBookListService.findByUserId(userId);
		
		//共通処理にしたためコメントアウト
//		int NumOfPendingApproval = countPendingApproval.countPendingApproval(userId);
//		model.addAttribute("NumOfPendingApproval", NumOfPendingApproval);
		
		model.addAttribute("categoryList", categoryList);
		List<Group> belongGroupList = showGroupManagementService.showGroupListByBelongUserIdAndRelationStatus(loginUser.getUser().getUserId(), 1);
		if(belongGroupList==null) {
			return "redirect:/tutorial";
		}
		
		return "book/book_list";
	}
	
	@RequestMapping("/show_one_category")
	public String showOneCategoryBookList(Model model, @AuthenticationPrincipal LoginUser loginUser, Integer categoryId, Integer page) {
		if (page == null) {
			page = 1;
		}
		
		Integer userId = loginUser.getUser().getUserId();
//		本の数を15で割った時の総ページ数
		Integer totalPageSize = showBookListOneCategory.findByUserIdAndCategoryId(userId, categoryId).get(0).getOwnedBookInfoList().size();
		Integer totalPageNum = 0;
		if(totalPageSize % 16 == 0) {
			totalPageNum = totalPageSize / 16;
		}else {
			totalPageNum = totalPageSize / 16 + 1;
		}
		
		List<Category> categoryList = ShowBookListOneCategoryByPageService.findByUserIdAndCategoryId(userId, categoryId, page);
		int NumOfPendingApproval = countPendingApproval.countPendingApproval(userId);
		model.addAttribute("NumOfPendingApproval", NumOfPendingApproval);
		model.addAttribute("page", page);
		model.addAttribute("totalPageNum", totalPageNum);
		model.addAttribute("categoryList", categoryList);
		return "book/book_list_category";
	}
}
