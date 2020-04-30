package com.honcari.controller.book_rental;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.LoginUser;
import com.honcari.form.ExtendRequestForm;
import com.honcari.service.book_rental.SendExtendRequestService;

/**
 * 貸出延長申請を送るコントローラ.
 * 
 * @author shumpei
 *
 */
@RequestMapping("/book_rental")
@Controller
public class SendExtendRequestController {

	@Autowired
	private SendExtendRequestService sendExtendRequestService;

	/**
	 * 貸出延長申請を送る.
	 * 
	 * @param model              リクエストスコープ
	 * @param loginUser          ログインユーザー
	 * @param form               フォーム
	 * @param result             エラーチェック
	 * @param redirectAttributes リダイレクトスコープ
	 * @return 貸出情報一覧画面
	 */
	@RequestMapping(value = "/send_extend", method = RequestMethod.POST)
	public String sendExtendRentalRequest(Model model, @AuthenticationPrincipal LoginUser loginUser,
			@Validated ExtendRequestForm form, BindingResult result, RedirectAttributes redirectAttributes) {
		String updateUserName = loginUser.getUser().getName();

		if (result.hasErrors()) {
			return "redirect:/book_rental/show_list";
		}

		// 貸出期限のエラーチェック
		String inputDeadline = form.getDeadline();
		LocalDate sendRequestDate = LocalDate.now();
		LocalDate deadlineDate = LocalDate.parse(inputDeadline);
		LocalDate maxRequestDate = sendRequestDate.plusMonths(2);
		if (deadlineDate.isBefore(sendRequestDate)) {
			result.rejectValue("deadline", "500", "貸出期限は今日以降の日付を入力してください");
		}
		if (deadlineDate.isAfter(maxRequestDate)) {
			result.rejectValue("deadline", "500", "貸出期限は本日から2か月以内の日付を入力してください");
		}
		if (result.hasErrors()) {
			return "redirect:/book_rental/show_list";
		}

		// 貸出申請を処理する
		try {
			sendExtendRequestService.sendExtendRequest(form, updateUserName);
			// TODO 貸し手にメール送信
			redirectAttributes.addFlashAttribute("successMessage", "貸出延長リクエストを送信しました！");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.rejectValue("deadline", "500", "貸出申請に失敗しました");
		}
		return "redirect:/book_rental/show_list";
	}

}
