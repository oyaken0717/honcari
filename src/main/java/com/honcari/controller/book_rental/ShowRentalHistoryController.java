package com.honcari.controller.book_rental;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.BookRental;
import com.honcari.domain.LoginUser;
import com.honcari.service.book_rental.ShowRentalHistoryService;

/**
 * レンタル履歴を表示するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@RequestMapping("/book_rental")
@Controller
public class ShowRentalHistoryController {
	
	@Autowired
	private ShowRentalHistoryService showLentalHistoryService;
	
	/**
	 * レンタル履歴画面へ遷移するメソッド.
	 * 
	 * @param loginUser ログイン中のユーザー
	 * @param model リクエストスコープ
	 * @return レンタル履歴画面
	 */
	@RequestMapping("/show_history")
	public String showLentalHistory(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		List<BookRental> borrowedList = showLentalHistoryService.showBorrowedList(loginUser.getUser().getUserId());
		List<BookRental> lentList = showLentalHistoryService.showlentList(loginUser.getUser().getUserId());
		model.addAttribute("borrowedList", borrowedList);
		model.addAttribute("lentList", lentList);
		return "/book_rental/rental_history";
	}

}
