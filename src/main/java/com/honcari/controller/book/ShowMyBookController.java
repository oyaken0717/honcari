package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.common.MyBookTypeEnum;
import com.honcari.domain.LoginUser;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.service.book.GetOwnedBookInfoCountService;
import com.honcari.service.book.ShowOwnBooksByCategoryIdService;
import com.honcari.service.book.ShowOwnAllBooksService;

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
	private ShowOwnAllBooksService showOwnAllBooksService;
	
	@Autowired
	private ShowOwnBooksByCategoryIdService showOwnBooksByCategoryIdService;
	
	@Autowired
	private GetOwnedBookInfoCountService getOwnedBookInfoCountService;
	
	
	/**
	 * 自身が登録している書籍一覧を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param page 取得したいページ
	 * @param loginUser ログインしているユーザ
	 * @return マイブック一覧
	 */
	@RequestMapping("/show_mybook")
	public String ShowMyBook(Model model, Integer page, @AuthenticationPrincipal LoginUser loginUser) {
		
		//owned_book_infoテーブル内のデータ件数を取得、20件1ページにて総ページ数を計算する
		Integer pageCount = getOwnedBookInfoCountService.getOwnedBookInfoCount(loginUser.getUser().getUserId());
		if(pageCount % 20 != 0) {
			pageCount = pageCount / 20 + 1;
		}else if(pageCount % 20 == 0) {
			pageCount = pageCount / 20;
		}
		
		//ページ数に対応したデータを取得し、リクエストスコープに格納する
		if(page == null) {
			page = 0;
		}else {
			page = page * 20;
		}
		List<OwnedBookInfo> ownedBookInfoList = showOwnAllBooksService.showOwnAllBook(loginUser.getUser().getUserId(), page);
		if(ownedBookInfoList.size() == 0) {
			model.addAttribute("errorMessage", "書籍が登録されていません。");
			model.addAttribute("categoryNum", MyBookTypeEnum.MY_BOOK_LIST.getKey());
		}else {
			model.addAttribute("ownedBookInfoList", ownedBookInfoList);
			model.addAttribute("categoryNum", MyBookTypeEnum.MY_BOOK_LIST.getKey());
			//総ページ数
			model.addAttribute("pageCount", pageCount);
			//現在のページ
			model.addAttribute("page", page);
		}
		return "book/mybook";
	}
	
	/**
	 * 自身が登録している書籍一覧をカテゴリ別に表示する.
	 * 
	 * @param categoryId カテゴリid
	 * @param page 取得したいページ
	 * @param model リクエストスコープ
	 * @param loginUser ログインしているユーザ
	 * @return マイブック一覧(カテゴリ毎)
	 */
	@RequestMapping("/show_mybook_category")
	public String showMyBookGroupByCategory(Integer categoryId, Integer page, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		
		//owned_book_infoテーブル内のデータ件数を取得する(カテゴリid毎)
		Integer pageCount = getOwnedBookInfoCountService.getOwnedBookInfoCountByCategoryId(loginUser.getUser().getUserId(), categoryId);
		if(pageCount % 20 != 0) {
			pageCount = pageCount / 20 + 1;
		}else if(pageCount % 20 == 0) {
			pageCount = pageCount / 20;
		}
		
		//ページ数に対応したデータを取得し、リクエストスコープに格納する
		if(page == null) {
			page = 0;
		}else {
			page = page * 20;
		}
		List<OwnedBookInfo> ownedBookInfoList = showOwnBooksByCategoryIdService.findByCategoryId(loginUser.getUser().getUserId(), categoryId, page);
		if(ownedBookInfoList.size() == 0) {
			model.addAttribute("errorMessage", "該当カテゴリの書籍が登録されていません。");
			model.addAttribute("categoryNum", categoryId);
		}else {
			model.addAttribute("ownedBookInfoList", ownedBookInfoList);
			model.addAttribute("categoryNum", categoryId);
			//総ページ数
			model.addAttribute("pageCount", pageCount);
			//現在のページ
			model.addAttribute("page", page);
		}
		return "book/mybook";
	}
}