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
import com.honcari.form.RentalRequestForm;
import com.honcari.service.book_rental.SearchByBorrowerService;
import com.honcari.service.book_rental.SearchByOwnerService;

/**
 * 貸出承認待ちの本一覧を表示する.
 * 
 * @author shumpei
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/book_rental")
public class ShowPendingListController {
	
	@Autowired
	private SearchByOwnerService searchByOwnerService;

	@Autowired
	private SearchByBorrowerService searchByBorrowerService;

	@ModelAttribute
	public RentalRequestForm setUpRentalRequestForm() {
		return new RentalRequestForm();
	}
	
	/**
	 * 貸出管理画面を表示する.
	 * 
	 * @param userId ユーザーID
	 * @param model  リクエストスコープ
	 * @return 貸出管理画面
	 */
	@RequestMapping("/show_pending_list")
	public String showPendingList(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = loginUser.getUser().getUserId();
		List<BookRental> BookRentalListByOwner = searchByOwnerService.searchRentalListByOwner(userId);
		List<BookRental> BookRentalListByBorrower = searchByBorrowerService.searchRentalListByBorrower(userId);

		// 貸す承認待ち本リスト
		List<BookRental> lendPendingList = BookRentalListByOwner.stream()
				.filter(bookRental -> bookRental.getRentalStatus() == RentalStatusEnum.WAIT_APPROVAL.getValue()
						|| bookRental.getRentalStatus() == RentalStatusEnum.WAIT_EXTEND.getValue())
				.collect(Collectors.toList());
		// 借りる承認待ち本リスト
		List<BookRental> borrowPendingList = BookRentalListByBorrower.stream()
				.filter(bookRental -> bookRental.getRentalStatus() == RentalStatusEnum.WAIT_APPROVAL.getValue()
						|| bookRental.getRentalStatus() == RentalStatusEnum.WAIT_EXTEND.getValue())
				.collect(Collectors.toList());

		model.addAttribute("lendPendingList", lendPendingList);
		model.addAttribute("borrowPendingList", borrowPendingList);
		return "book_rental/rental_pending_list";
	}

}
