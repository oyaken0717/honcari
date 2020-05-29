package com.honcari.controller.book_rental;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.controller.book.ShowBookDetailController;
import com.honcari.domain.LoginUser;
import com.honcari.form.ExtendRequestForm;
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

	@ModelAttribute
	public ExtendRequestForm setUpExtendRequestForm() {
		return new ExtendRequestForm();
	}

	/**
	 * 貸出リクエストを送る.
	 * 
	 * @param model              リクエストスコープ
	 * @param loginUser          ログインユーザー
	 * @param form               フォーム
	 * @param result             エラーチェック
	 * @param redirectAttributes リダイレクトスコープ
	 * @return 貸出申請管理画面
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String sendRentalRequest(Model model, @AuthenticationPrincipal LoginUser loginUser,
			@Validated RentalRequestForm form, BindingResult result, RedirectAttributes redirectAttributes) {
		Integer borrowUserId = loginUser.getUser().getUserId();
		String borrowUserName = loginUser.getUser().getName();
		Integer ownedBookInfoId = form.getOwnedBookInfoId();
		Integer bookStatus = form.getBookStatus();
		Integer ownerId = form.getOwnerId();
		Integer ownedBookInfoVersion = form.getOwnedBookInfoVersion();

		// 貸出可以外の本を借りた場合と所有者自らがリクエストを送った場合にエラー
		if (bookStatus != 1 || ownerId == borrowUserId) {
			return "error/500";
		}

		if (result.hasErrors()) {
			return showBookDetailController.showBookDetail(model, ownedBookInfoId, loginUser);
		}

		// 貸出期間のエラーチェック
		Date requestBeginning = Date.valueOf(form.getRequestBeginning());
		Date requestDeadline = Date.valueOf(form.getRequestDeadline());
		String errorMessage = checkRequestDeadline(form.getRequestBeginning(), form.getRequestDeadline());
		if (Objects.nonNull(errorMessage)) {
			result.rejectValue("requestDeadline", "500", errorMessage);
			return showBookDetailController.showBookDetail(model, ownedBookInfoId, loginUser);
		}

		// 貸出申請を処理する
		try {
			sendRentalRequestService.sendRentalRequest(ownedBookInfoId, borrowUserId, borrowUserName, requestBeginning,
					requestDeadline, ownedBookInfoVersion);
			redirectAttributes.addFlashAttribute("successMessage", "貸出リクエストを送信しました！");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.rejectValue("requestDeadline", "500", "貸出申請に失敗しました");
			return showBookDetailController.showBookDetail(model, ownedBookInfoId, loginUser);
		}
		return "redirect:/book_rental/show_pending_list";
	}

	/**
	 * 貸出期間のエラーチェックを行う.
	 * 
	 * @param requestBeginning 申請貸出開始日
	 * @param requestDeadline 申請貸出期限
	 * @return エラーメッセージ
	 */
	public String checkRequestDeadline(String requestBeginning, String requestDeadline) {
		LocalDate sendRequestDate = LocalDate.now();
		LocalDate beginningDate = LocalDate.parse(requestBeginning);
		LocalDate deadlineDate = LocalDate.parse(requestDeadline);
		LocalDate maxRequestDate = sendRequestDate.plusMonths(1);
		if (beginningDate.isBefore(sendRequestDate)) {
			return "貸出開始日は本日以降の日付を入力してください";
		}
		if (deadlineDate.isBefore(sendRequestDate)) {
			return "貸出期限は本日以降の日付を入力してください";
		}
		if (deadlineDate.isBefore(beginningDate)) {
			return "貸出期限は貸出開始日以降の日付を入力してください";
		}
		if (deadlineDate.isAfter(maxRequestDate)) {
			return "貸出期限は本日から1ヶ月以内の日付を入力してください";
		}
		if (deadlineDate.isBefore(beginningDate.plusDays(7))) {
			return "最低貸出期間は１週間です";
		}
		return null;
	}

}
