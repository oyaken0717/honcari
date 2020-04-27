package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.LoginUser;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.service.book.ShowMyAllBooksService;
import com.honcari.service.book.ShowMyBooksByCategoryIdService;

/**
 * マイブックを表示するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@RequestMapping("/book")
public class ShowMyBookController {
	
	@Autowired
	private ShowMyAllBooksService showMyAllBooksService;
	
	@Autowired
	private ShowMyBooksByCategoryIdService showMyBooksByCategoryIdService;
	
	/**
	 * 自身が登録している書籍一覧を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param loginUser ログインしているユーザ情報
	 * @return マイブックページ(自身が登録している書籍一覧)
	 */
	@RequestMapping("/show_mybook")
	public String ShowMyBook(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<OwnedBookInfo> ownedBookInfoList = showMyAllBooksService.ShowMyAllBook(loginUser.getUser().getUserId());
		model.addAttribute("ownedBookInfoList", ownedBookInfoList);
		return "book/mybook";
	}
	
	/**
	 * 自身が登録している書籍一覧をカテゴリ別に表示する
	 * 
	 * @param categoryId カテゴリID
	 * @param model リクエストスコープ
	 * @param loginUser ログインしているユーザ情報
	 * @return マイブックページ(自身が登録しているカテゴリ別の書籍一覧)
	 */
	@RequestMapping("/show_mybook_category")
	public String showMyBookGroupByCategory(Integer categoryId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		if(categoryId == null) {
			categoryId = 0;
		}
		List<OwnedBookInfo> ownedBookInfoList = showMyBooksByCategoryIdService.findByCategoryId(loginUser.getUser().getUserId(), categoryId);
		if(ownedBookInfoList.size() == 0) {
			model.addAttribute("errorMessage", "該当カテゴリの書籍が登録されていません。");
		}else {
			model.addAttribute("ownedBookInfoList", ownedBookInfoList.get(0));
		}
		return "book/mybook";
	}
}