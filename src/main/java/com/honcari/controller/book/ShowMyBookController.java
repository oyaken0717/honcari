package com.honcari.controller.book;

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
import com.honcari.service.book.ShowMyBookService;
import com.honcari.service.user.ShowMyPageService;

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