package com.honcari.controller.book_rental;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.honcari.controller.book.ShowBookDetailController;
import com.honcari.domain.LoginUser;
import com.honcari.form.RentalRequestForm;
import com.honcari.service.book_rental.SendRentalRequestService;

/**
 * 貸出リクエストを送るコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("/book_rental")
public class SendRequestController {

	@Autowired
	private ShowBookDetailController showBookDetailController;

	@Autowired
	private SendRentalRequestService sendRentalRequestService;

	@ModelAttribute
	public RentalRequestForm setUpForm() {
		return new RentalRequestForm();
	}

	/**
	 * 貸出リクエストを送る.
	 * 
	 * @param model  リクエストスコープ
	 * @param form   フォーム
	 * @param result エラーチェック
	 * @return 申請状況一覧画面
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String sendLendingRequest(Model model, @AuthenticationPrincipal LoginUser loginUser,
			@Validated RentalRequestForm form, BindingResult result) {
		Integer borrowUserId = loginUser.getUser().getUserId();
		String borrowUserName = loginUser.getUser().getName();
		Integer ownedBookInfoId = form.getOwnedBookInfoId();
		Integer bookStatus = form.getBookStatus();
		Integer ownerId = form.getOwnerId();
		if (bookStatus != 1 || ownerId == borrowUserId) {
			result.rejectValue("deadline", "500", "不正なリクエストが行われました");
		}

		if (result.hasErrors()) {
			return showBookDetailController.showBookDetail(model, ownedBookInfoId);
		}

		String inputDeadline = form.getDeadline();
		Date deadline = Date.valueOf(inputDeadline);
		LocalDate requestDate = LocalDate.now();
		LocalDate deadlineDate = LocalDate.parse(inputDeadline);
		LocalDate maxRequestDate = requestDate.plusMonths(2);
		
		if (deadlineDate.isBefore(requestDate)) {
			result.rejectValue("deadline", "500", "貸出期限は今日以降の日付を入力してください");
		}

		if (deadlineDate.isAfter(maxRequestDate)) {
			result.rejectValue("deadline", "500", "貸出期限は本日から2か月以内の日付を入力してください");
		}

		if (result.hasErrors()) {
			return showBookDetailController.showBookDetail(model, ownedBookInfoId);
		}

		sendRentalRequestService.sendRentalRequest(ownedBookInfoId, borrowUserId, borrowUserName, deadline);
		// TODO 貸し手にメール送信
		return "redirect:/book_rental/show_list";
	}

}
