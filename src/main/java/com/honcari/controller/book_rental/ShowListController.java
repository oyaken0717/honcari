package com.honcari.controller.book_rental;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.BookRental;
import com.honcari.domain.LoginUser;
import com.honcari.service.book_rental.SearchByBorrowerService;
import com.honcari.service.book_rental.SearchByOwnerService;

/**
 * 貸出管理画面を表示する.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("/book_rental")
public class ShowListController {
	
	@Autowired
	private SearchByOwnerService searchByOwnerService;
	
	@Autowired
	private SearchByBorrowerService searchByBorrowerService;
	
	/**
	 * 貸出管理画面を表示する.
	 * 
	 * @param userId ユーザーID
	 * @param model  リクエストスコープ
	 * @return 貸出管理画面
	 */
	@RequestMapping("/show_list")
	public String showList(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = loginUser.getUser().getUserId();
		List<BookRental> BookRentalListByOwner = searchByOwnerService.searchRentalListByOwner(userId);
		List<BookRental> BookRentalListByBorrower = searchByBorrowerService.searchRentalListByBorrower(userId);

		// 貸出承認待ち本リスト
		List<BookRental> lendPendingList = BookRentalListByOwner.stream()
				.filter(bookRental -> bookRental.getRentalStatus() == 0).collect(Collectors.toList());
		// 貸し出し中本リスト
		List<BookRental> lendList = BookRentalListByOwner.stream()
				.filter(bookRental -> bookRental.getRentalStatus() != 0).collect(Collectors.toList());
		// レンタル承認待ち本リスト
		List<BookRental> borrowPendingList = BookRentalListByBorrower.stream()
				.filter(bookRental -> bookRental.getRentalStatus() == 0).collect(Collectors.toList());
		// レンタル中本リスト
		List<BookRental> borrowList = BookRentalListByBorrower.stream()
				.filter(bookRental -> bookRental.getRentalStatus() != 0).collect(Collectors.toList());

		model.addAttribute("lendPendingList", lendPendingList);
		model.addAttribute("lendList", lendList);
		model.addAttribute("borrowPendingList", borrowPendingList);
		model.addAttribute("borrowList", borrowList);
		return "book_rental/rental_list";
	}

}
