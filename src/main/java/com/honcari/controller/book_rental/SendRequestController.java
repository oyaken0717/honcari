package com.honcari.controller.book_rental;

import java.sql.Date;

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
	public String sendLendingRequest(Model model, @AuthenticationPrincipal LoginUser loginUser, @Validated RentalRequestForm form, BindingResult result) {
		Integer borrowUserId = loginUser.getUser().getUserId();
		Integer ownedBookInfoId = form.getOwnedBookInfoId();
		Integer bookStatus = form.getBookStatus();
		Integer ownerId = form.getOwnerId();
		if (result.hasErrors()) {
			return showBookDetailController.showBookDetail(model, ownedBookInfoId);
		}
		if (bookStatus != 1 || ownerId == borrowUserId) {
			result.rejectValue("deadline", "500", "不正なリクエストが行われました");
			return showBookDetailController.showBookDetail(model, ownedBookInfoId);
		}
		Date deadline = Date.valueOf(form.getDeadline());
		sendRentalRequestService.sendRentalRequest(ownedBookInfoId, borrowUserId, deadline);
		// TODO 貸し手にメール送信
		return "redirect:/book_rental/show_list";
	}

}
