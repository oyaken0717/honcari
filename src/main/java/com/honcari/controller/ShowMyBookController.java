package com.honcari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.form.EditUserForm;
import com.honcari.service.ShowMyBookService;
import com.honcari.service.ShowMyPageService;

/**
 * マイブックを表示するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class ShowMyBookController {
	
	@Autowired
	private ShowMyBookService showMyBookService;
	
	@Autowired
	private ShowMyPageService showMyPageService;
	
	@ModelAttribute
	public EditUserForm setUpEditUserForm() {
		return new EditUserForm();
	}
//
//TODO 動作確認後問題なければ消します	
//	/**
//	 * マイブックに遷移するメソッド
//	 * 
//	 * @param myPageType ページタイプ
//	 * @param model リクエストスコープ
//	 * @param loginUser ログイン中のユーザ
//	 * @return
//	 */
//	@RequestMapping("/show_my_book")
//	public String showMyPage(Integer myBookType, Model model, @AuthenticationPrincipal LoginUser loginUser) {
//		Integer userId = 1; //TODO SpringSecurity実装後に変更する
//		if(myBookType == null) {
//			myBookType = 0;
//		}
//		User user = null;
//		List<User> userList = new ArrayList<>();
//		String errorMessage = "該当する書籍がありませんでした。";
//		switch(MyBookType.of(myBookType)) {
//		case MY_BOOK_LIST:
//			user = showMyPageService.showUser(userId);
//			model.addAttribute("user", user);
//			break;
//		case MY_BOOK_FICTION:
//			userList = showMyBookService.findByCategoryId(userId, 1);
//			if(userList.size() == 0) {
//				model.addAttribute("errorMessage", errorMessage);
//			}else {
//			model.addAttribute("user", user);
//			}
//			break;
//		case MY_BOOK_BUSINESS:
//			user = showMyPageService.findByCategoryId(userId, 2);
//			model.addAttribute("user", user);
//			break;
//		case MY_BOOK_EDUCATION:
//			user = showMyPageService.findByCategoryId(userId, 3);
//			model.addAttribute("user", user);
//			break;
//		case MY_BOOK_MAP:
//			user = showMyPageService.findByCategoryId(userId, 4);
//			model.addAttribute("user", user);
//			break;
//		case MY_BOOK_HOBBY:
//			user = showMyPageService.findByCategoryId(userId, 5);
//			model.addAttribute("user", user);
//			break;
//		case MY_BOOK_ENTERTAINMENT:
//			user = showMyPageService.findByCategoryId(userId, 6);
//			model.addAttribute("user", user);
//			break;
//		case MY_BOOK_LANGUAGE:
//			user = showMyPageService.findByCategoryId(userId, 7);
//			model.addAttribute("user", user);
//			break;
//		case MY_BOOK_CHILDREN:
//			user = showMyPageService.findByCategoryId(userId, 8);
//			model.addAttribute("user", user);
//			break;
//		case MY_BOOK_OTHER:
//			user = showMyPageService.findByCategoryId(userId, 9);
//			model.addAttribute("user", user);
//			break;
//		default:
//			user = showMyPageService.showUser(1);
//			model.addAttribute("user",user);
//		}
//		model.addAttribute("myBookType", myBookType);
//		return "mybook";
//	}
//	
	
	/**
	 * 自身が登録している書籍一覧を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param loginUser ログインしているユーザ情報
	 * @return マイブックページ(自身が登録している書籍一覧)
	 */
	@RequestMapping("/show_my_book")
	public String ShowMyBook(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = showMyPageService.showUser(loginUser.getUser().getId());
		model.addAttribute("user",user);
		return "mybook";
	}
	
	/**
	 * 自身が登録している書籍一覧をカテゴリ別に表示する
	 * 
	 * @param categoryId カテゴリID
	 * @param model リクエストスコープ
	 * @param loginUser ログインしているユーザ情報
	 * @return マイブックページ(自身が登録しているカテゴリ別の書籍一覧)
	 */
	@RequestMapping("/show_my_book_category")
	public String showMyBookGroupByCategory(Integer categoryId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		if(categoryId == null) {
			categoryId = 0;
		}
		List<User> userList = showMyBookService.findByCategoryId(loginUser.getUser().getId(), categoryId);
		if(userList.size() == 0 || userList.get(0).getBookList().size() == 0) {
			model.addAttribute("errorMessage", "該当カテゴリの書籍が登録されていません。");
		}else {
			model.addAttribute("user", userList.get(0));
		}
		return "mybook";
	}
}