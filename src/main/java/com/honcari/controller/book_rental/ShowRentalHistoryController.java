package com.honcari.controller.book_rental;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.common.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.BookRental;
import com.honcari.domain.LoginUser;
import com.honcari.form.RentalRequestForm;
import com.honcari.service.book_rental.SearchBorrowedHistoryService;
import com.honcari.service.book_rental.SearchLentHistoryService;

/**
 * レンタル履歴を表示するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/book_rental")
public class ShowRentalHistoryController {
	
	@Autowired
	private SearchBorrowedHistoryService searchBorrowedHistoryService;
	
	@Autowired
	private SearchLentHistoryService searchLentHistoryService;
	
	@ModelAttribute
	public RentalRequestForm setUpRentalRequestForm() {
		RentalRequestForm form = new RentalRequestForm();
		LocalDate defaultBeginning = LocalDate.now();
		LocalDate defaultDeadline = defaultBeginning.plusWeeks(2);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		form.setRequestBeginning(formatter.format(defaultBeginning));
		form.setRequestDeadline(formatter.format(defaultDeadline));		
		return form;
	}
	
	/** 1ページに表示する数 */
	private static final int VIEW_SIZE = 10;
	
	/**
	 * レンタル履歴画面へ遷移するメソッド.
	 * 
	 * @param loginUser ログイン中のユーザー
	 * @param model リクエストスコープ
	 * @return レンタル履歴画面
	 */
	@RequestMapping("/show_history")
	public String showLentalHistory(Integer borrowedPageNum, Integer lentPageNum, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<BookRental> borrowedList = searchBorrowedHistoryService.showBorrowedList(loginUser.getUser().getUserId());
		if(Objects.isNull(borrowedPageNum)) {
			borrowedPageNum = 1;
		}
		if(!Objects.isNull(borrowedList)) {
			Page<BookRental> borrowedPage = searchBorrowedHistoryService.pagingBorrowedList(borrowedPageNum, VIEW_SIZE, borrowedList);
			model.addAttribute("borrowedPage", borrowedPage);
			model.addAttribute("borrowedPageNumbers", calcPageNumbers(borrowedPage));
		}
		
		List<BookRental> lentList = searchLentHistoryService.showlentList(loginUser.getUser().getUserId());
		if(Objects.isNull(lentPageNum)) {
			lentPageNum = 1;
		}
		if(!Objects.isNull(lentList)) {
			Page<BookRental> lentPage = searchLentHistoryService.pagingLentList(borrowedPageNum, VIEW_SIZE, lentList);
			model.addAttribute("lentPage", lentPage);
			model.addAttribute("lentPageNumbers", calcPageNumbers(lentPage));
		}
		
		return "book_rental/rental_history";
	}
	
	/**
	 * ページングのリンクに使うページ数を返すメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @param rentalPage ページ
	 * @return ページ数
	 */
	private List<Integer> calcPageNumbers(Page<BookRental> rentalPage) {
		int totalPages = rentalPage.getTotalPages();
		List<Integer> pageNumbers = null;
		if (totalPages > 0) {
			pageNumbers = new ArrayList<Integer>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
		}
		return pageNumbers;
	}

}
