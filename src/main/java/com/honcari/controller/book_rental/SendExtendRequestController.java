//貸出延長機能を削除するためコメントアウトby湯口

//package com.honcari.controller.book_rental;
//
//import java.time.LocalDate;
//import java.util.Objects;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.honcari.domain.LoginUser;
//import com.honcari.form.ExtendRequestForm;
//import com.honcari.form.RentalRequestForm;
//import com.honcari.service.book_rental.SendExtendRequestService;
//
///**
// * 貸出延長申請を送るコントローラ.
// * 
// * @author shumpei
// *
// */
//@RequestMapping("/book_rental")
//@Controller
//public class SendExtendRequestController {
//
//	@Autowired
//	private SendExtendRequestService sendExtendRequestService;
//
//	@Autowired
//	private ShowListController showListController;
//
//	@ModelAttribute
//	public RentalRequestForm setUpForm() {
//		return new RentalRequestForm();
//	}
//
//	@ModelAttribute
//	public ExtendRequestForm setUpExtendRequestForm() {
//		return new ExtendRequestForm();
//	}
//
//	/**
//	 * 貸出延長申請を送る.
//	 * 
//	 * @param model              リクエストスコープ
//	 * @param loginUser          ログインユーザー
//	 * @param form               フォーム
//	 * @param result             エラーチェック
//	 * @param redirectAttributes リダイレクトスコープ
//	 * @return 貸出情報一覧画面
//	 */
//	@RequestMapping(value = "/send_extend", method = RequestMethod.POST)
//	public String sendExtendRentalRequest(Model model, @AuthenticationPrincipal LoginUser loginUser,
//			@Validated ExtendRequestForm form, BindingResult result, RedirectAttributes redirectAttributes) {
//		String updateUserName = loginUser.getUser().getName();
//
//		if (result.hasErrors()) {
//			return showListController.showList(loginUser, model);
//		}
//
//		// 貸出期限のエラーチェック
//		String errorMessage = checkRequestDeadline(LocalDate.parse(form.getDefaultDeadline()),
//				LocalDate.parse(form.getRequestDeadline()));
//		if (Objects.nonNull(errorMessage)) {
//			result.rejectValue("requestDeadline", "500", "errorMessage");
//			return showListController.showList(loginUser, model);
//		}
//
//		// 貸出申請を処理する
//		try {
//			sendExtendRequestService.sendExtendRequest(form, updateUserName);
//			// TODO 貸し手にメール送信
//			redirectAttributes.addFlashAttribute("successMessage", "貸出延長リクエストを送信しました！");
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			redirectAttributes.addFlashAttribute("errorMessage", "貸出延長リクエストに失敗しました！");
//		}
//		return "redirect:/book_rental/show_list";
//	}
//
//	/**
//	 * 貸出期限のチェックを行う.
//	 * 
//	 * @param defautDeadline  元々の貸出期限
//	 * @param requestDeadline 申請した貸出期限
//	 * @return エラーメッセージ
//	 */
//	public String checkRequestDeadline(LocalDate defautDeadline, LocalDate requestDeadline) {
//		LocalDate maxRequestDate = defautDeadline.plusMonths(1);
//		if (requestDeadline.isBefore(defautDeadline)) {
//			return "延長期限は元々の期限日以降の日付を入力してください";
//		}
//		if (requestDeadline.isAfter(maxRequestDate)) {
//			return "延長期限は元々の期限日から1か月以内の日付を入力してください";
//		}
//		return null;
//	}
//
//}
