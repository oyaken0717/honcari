package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.LoginUser;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.service.book.GetOwnedBookInfoCountService;
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
	
	@Autowired
	private GetOwnedBookInfoCountService getOwnedBookInfoCountService;
	
	/**
	 * 自身が登録している書籍一覧を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param loginUser ログインしているユーザ情報
	 * @return マイブックページ(自身が登録している書籍一覧)
	 */
	@RequestMapping("/show_mybook")
	public String ShowMyBook(Model model, Integer page, @AuthenticationPrincipal LoginUser loginUser) {
		//owned_book_infoテーブル内のデータ件数を取得する
		Integer pageCount = getOwnedBookInfoCountService.getOwnedBookInfoCount(loginUser.getUser().getUserId());
		if(pageCount != null && pageCount % 20 != 0) {
			pageCount = pageCount / 20 + 1;
		}else if(pageCount != null && pageCount % 20 == 0) {
			pageCount = pageCount / 20;
		}
		//ページ数に対応したownedBookInfoを20件取得する
		if(page == null) {
			page = 0;
		}else {
			page = page * 20;
		}
		List<OwnedBookInfo> ownedBookInfoList = showMyAllBooksService.ShowMyAllBook(loginUser.getUser().getUserId(), page);
		if(ownedBookInfoList.size() == 0) {
			model.addAttribute("errorMessage", "書籍が登録されていません。");
		}else {
			model.addAttribute("ownedBookInfoList", ownedBookInfoList);
			model.addAttribute("categoryNum", 0);
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("page", page);
			System.out.println(page + "に入っているおは");
		}
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
	public String showMyBookGroupByCategory(Integer categoryId, Integer page, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		System.out.println(categoryId);
		System.out.println(page);
		//owned_book_infoテーブル内のデータ件数を取得する(byカテゴリid)
		Integer pageCount = getOwnedBookInfoCountService.getOwnedBookInfoCountByCategoryId(loginUser.getUser().getUserId(), categoryId);
		if(pageCount != null && pageCount % 20 != 0) {
			pageCount = pageCount / 20 + 1;
		}else if(pageCount != null && pageCount % 20 == 0) {
			pageCount = pageCount / 20;
		}
		if(page == null) {
			page = 0;
		}else {
			page = page * 20;
		}
		List<OwnedBookInfo> ownedBookInfoList = showMyBooksByCategoryIdService.findByCategoryId(loginUser.getUser().getUserId(), categoryId, page);
		if(ownedBookInfoList.size() == 0) {
			model.addAttribute("errorMessage", "該当カテゴリの書籍が登録されていません。");
			model.addAttribute("categoryNum", categoryId);
		}else {
			model.addAttribute("ownedBookInfoList", ownedBookInfoList);
			model.addAttribute("categoryNum", categoryId);
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("page", page);
		}
		return "book/mybook";
	}
}