package com.honcari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.BookLending;
import com.honcari.service.BookService;

/**
 * 貸出承認待ちの本一覧を表示するコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("")
public class ShowLendingRequestManagementController {

	@Autowired
	private BookService bookService;

	/**
	 * 貸出承認待ちの本一覧を表示する.
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/to_lend_management")
	public String toLendManagement(Integer userId,Model model) {
		List<BookLending> bookLendingList = bookService.showWaitApprovalBookLendingList(userId);
		List<BookLending> bookBorrowingList = bookService.showWaitApprovalBookBorrowingList(userId);
		model.addAttribute("bookLendingList", bookLendingList);
		model.addAttribute("bookBorrowingList", bookBorrowingList);
		return "lend_management";
	}
}
