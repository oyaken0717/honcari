package com.honcari.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.BookLending;
import com.honcari.service.BookService;

@Controller
@RequestMapping("")
public class ShowLendingRequestManagementController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping("/to_lend_management")
	public String toLendManagement(Integer userId) {
		List<BookLending> bookLendingList = bookService.showWaitApprovalBookLendingList(userId);
		System.out.println(bookLendingList);
		return "lend_management";
	}
}
