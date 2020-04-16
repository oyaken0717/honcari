package com.honcari.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.form.LendingRequestForm;
import com.honcari.service.BookService;

/**
 * 貸出リクエストを送るコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("")
public class SendLendingRequestController {

	@Autowired
	private BookService bookService;

	@Autowired
	private ShowBookDetailController showBookDetailController;

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
	@RequestMapping("/send_lending_request")
	public String sendLendingRequest(Model model, @Validated LendingRequestForm form, BindingResult result) {
		Integer borrowUserId = 1; // TODO SpringSecurity実装後LoginUserへ置き換え
		Integer bookId = form.getBookId();
		Integer lendUserId = form.getLenderUserId();

		// TODO statusチェックを行う

		if (borrowUserId == lendUserId) {
			model.addAttribute("errorMessage", "不正なリクエストが行われました");
		}

		if (result.hasErrors()) {
			return showBookDetailController.showBookDetai(model, bookId);
		}

		Date deadline = Date.valueOf(form.getDeadline());
		bookService.runLendingBookRequest(bookId, lendUserId, borrowUserId, deadline);
		// TODO 貸し手にメール送信
		return "redirect:/";
	}

}
