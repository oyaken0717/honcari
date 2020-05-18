package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.service.book.GetOwnedBookInfoCountService;
import com.honcari.service.book.ShowOwnAllBooksService;
import com.honcari.service.book.ShowOwnBooksByCategoryIdService;
import com.honcari.service.user.SearchUserByUserIdService;

/**
 * ユーザーの所有書籍を表示するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/book")
public class ShowUserBookController {
	
	@Autowired
	private SearchUserByUserIdService searchUserByUserIdService;
	
	@Autowired
	private ShowOwnAllBooksService showOwnAllBooksService;
	
	@Autowired
	private ShowOwnBooksByCategoryIdService showOwnBooksByCategoryIdService;
	
	@Autowired
	private GetOwnedBookInfoCountService getOwnedBookInfoCountService;
	
	/**
	 * ユーザーの全所有書籍画面へ遷移するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param page ページ番号
	 * @param model リクエストスコープ
	 * @return ユーザーの所有書籍画面
	 */
	@RequestMapping("/show_user_book")
	public String showUserBook(Integer userId, Integer page, Model model) {
		//owned_book_infoテーブル内のデータ件数を取得する
		Integer pageCount = getOwnedBookInfoCountService.getOwnedBookInfoCount(userId);
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
		List<OwnedBookInfo> ownedBookInfoList = showOwnAllBooksService.showOwnAllBook(userId, page);
		if(ownedBookInfoList.size() == 0) {
			model.addAttribute("errorMessage", "書籍が登録されていません。");
			model.addAttribute("categoryNum", 0);
		}else {
			model.addAttribute("ownedBookInfoList", ownedBookInfoList);
			model.addAttribute("categoryNum", 0);
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("page", page);
		}
		model.addAttribute("user", searchUserByUserIdService.showUser(userId));
		return "book/user_book";
	}
	
	/**
	 * カテゴリー別所有書籍画面へ遷移するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param categoryId カテゴリーID
	 * @param page ページ番号
	 * @param model リクエストスコープ
	 * @return ユーザーの所有書籍画面
	 */
	@RequestMapping("/show_user_book_category")
	public String showUserBookCategory(Integer userId, Integer categoryId, Integer page, Model model) {
		//owned_book_infoテーブル内のデータ件数を取得する(byカテゴリid)
		Integer pageCount = getOwnedBookInfoCountService.getOwnedBookInfoCountByCategoryId(userId, categoryId);
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
		List<OwnedBookInfo> ownedBookInfoList = showOwnBooksByCategoryIdService.findByCategoryId(userId, categoryId, page);
		if(ownedBookInfoList.size() == 0) {
			model.addAttribute("errorMessage", "該当カテゴリの書籍が登録されていません。");
			model.addAttribute("categoryNum", categoryId);
		}else {
			model.addAttribute("ownedBookInfoList", ownedBookInfoList);
			model.addAttribute("categoryNum", categoryId);
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("page", page);
		}
		model.addAttribute("user", searchUserByUserIdService.showUser(userId));
		return "book/user_book";
	}
}
