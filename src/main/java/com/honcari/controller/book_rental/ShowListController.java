package com.honcari.controller.book_rental;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.common.RentalStatusEnum;
import com.honcari.common.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.BookRental;
import com.honcari.domain.LoginUser;
import com.honcari.form.ExtendRequestForm;
import com.honcari.form.RentalRequestForm;
import com.honcari.service.book_rental.SearchByBorrowerService;
import com.honcari.service.book_rental.SearchByOwnerService;

/**
 * 貸出管理画面を表示する.
 * 
 * @author shumpei
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/book_rental")
public class ShowListController {

	@Autowired
	private SearchByOwnerService searchByOwnerService;

	@Autowired
	private SearchByBorrowerService searchByBorrowerService;

	@ModelAttribute
	public RentalRequestForm setUpRentalRequestForm() {
		return new RentalRequestForm();
	}

	@ModelAttribute
	public ExtendRequestForm setUpExtendRequestForm() {
		return new ExtendRequestForm();
	}

	/**
	 * 貸出中の本一覧画面を表示する.
	 * 
	 * @param loginUser ログインユーザー
	 * @param model  リクエストスコープ
	 * @return 貸出中の本一覧画面
	 */
	@RequestMapping("/show_list")
	public String showList(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = loginUser.getUser().getUserId();
		List<BookRental> BookRentalListByOwner = searchByOwnerService.searchRentalListByOwner(userId);
		List<BookRental> BookRentalListByBorrower = searchByBorrowerService.searchRentalListByBorrower(userId);

		// 貸している本リスト
		List<BookRental> lendList = BookRentalListByOwner.stream()
				.filter(bookRental -> bookRental.getRentalStatus() == RentalStatusEnum.APPROVED.getValue()
						|| bookRental.getRentalStatus() == RentalStatusEnum.WAIT_RETURNING.getValue())
				.collect(Collectors.toList());

		// 借りている本リスト
		List<BookRental> borrowList = BookRentalListByBorrower.stream()
				.filter(bookRental -> bookRental.getRentalStatus() == RentalStatusEnum.APPROVED.getValue()
						|| bookRental.getRentalStatus() == RentalStatusEnum.WAIT_RETURNING.getValue())
				.collect(Collectors.toList());

		model.addAttribute("lendList", lendList);
		model.addAttribute("borrowList", borrowList);
		return "book_rental/rental_list";
	}

}
