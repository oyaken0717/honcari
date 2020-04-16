package com.honcari.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.BookLending;
import com.honcari.form.LendingRequestForm;
import com.honcari.service.BookService;

/**
 * 貸出リクエストを送るコントローラ.
 * 
 * @author shumpei
 *
 */
@RequestMapping("")
@Controller
public class SendLendingRequestController {
	
	@Autowired
	private BookService bookService;
	
	@ModelAttribute
	public LendingRequestForm setUpForm() {
		return new LendingRequestForm();
	}
	
	/**
	 * 貸出リクエストを送る.
	 * 
	 * @param form フォーム
	 * @return 貸出承認待ち一覧画面
	 */
	@RequestMapping("/send-lending-request")
	public String sendLendingRequest(LendingRequestForm form) {
		Integer borrowUserId = 1; //SpringSecurity実装後LoginUserへ置き換え
		System.out.println(form);
		Integer bookId = form.getBookId();
		Integer lendUserId = form.getLenderUserId();
		Date deadline = Date.valueOf(form.getDeadline());
		bookService.runLendingBookRequest(bookId, lendUserId, borrowUserId, deadline);
		//TODO 貸し手にメール送信
		return "redirect:/";
	}
	
	/**
	 * 貸出リクエストに対し承認する.
	 * 
	 * @param form フォーム
	 * @return 貸出管理画面
	 */
	@RequestMapping("/approval_lending_request")
	public String approvalLendingRequest(LendingRequestForm form) {
		Integer bookId = form.getBookId();
		bookService.runApprovalLendingBookRequest(bookId);;
		//TODO 貸し手にメール送信
		return "redirect:/";

	}

}
