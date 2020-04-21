package com.honcari.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.BookLending;
import com.honcari.form.LendingRequestForm;
import com.honcari.service.BookService;

/**
 * 本の貸出挙動を管理するコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("")
public class BookMangementController {

	@Autowired
	private BookService bookService;

	@Autowired
	private ShowBookDetailController showBookDetailController;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public LendingRequestForm setUpForm() {
		return new LendingRequestForm();
	}

	/**
	 * 貸出リクエストを送る.
	 * 
	 * @param model  リクエストスコープ
	 * @param form   フォーム
	 * @param result エラーチェック
	 * @return 申請状況一覧画面
	 */
	@RequestMapping("/send_lending_request")
	public String sendLendingRequest(Model model, @Validated LendingRequestForm form, BindingResult result) {
		Integer borrowUserId = 1; // TODO SpringSecurity実装後LoginUserへ置き換え
		Integer bookId = form.getBookId();
		Integer lendUserId = form.getLenderUserId();
		Integer status = form.getStatus();

		// TODO エラーチェックの段階要検討
		if (status != 1) {
			model.addAttribute("errorMessage", "不正なリクエストが行われました");
			return showBookDetailController.showBookDetai(model, bookId);
		}

//		if (borrowUserId == lendUserId) {
//			model.addAttribute("errorMessage", "不正なリクエストが行われました");
//			return showBookDetailController.showBookDetai(model, bookId);
//		}

		if (result.hasErrors()) {
			return showBookDetailController.showBookDetai(model, bookId);
		}

		Date deadline = Date.valueOf(form.getDeadline());
		bookService.runLendingBookRequest(bookId, lendUserId, borrowUserId, deadline);
		// TODO 貸し手にメール送信
		return "redirect:/to_lend_management";
	}

	/**
	 * 貸出管理画面を表示する.
	 * 
	 * @param userId ユーザーID
	 * @param model  リクエストスコープ
	 * @return 貸出管理画面
	 */
	@RequestMapping("/to_lend_management")
	public String toLendManagement(Integer userId, Model model) {
		userId = 1; // TODO SS導入後にログインユーザーへ変更
		List<BookLending> lendBookLendingList = bookService.searchBookLendingListByLendUserId(userId);
		List<BookLending> borrowBookLendingList = bookService.searchBookLendingListByBorrowUserId(userId);

		// 貸出承認待ち本リスト
		List<BookLending> lendingBookListPendingApproval = lendBookLendingList.stream()
				.filter(bookLending -> bookLending.getLendingStatus() == 0).collect(Collectors.toList());
		// 貸し出し中本リスト
		List<BookLending> lendingBookList = lendBookLendingList.stream()
				.filter(bookLending -> bookLending.getLendingStatus() != 0).collect(Collectors.toList());
		// レンタル承認待ち本リスト
		List<BookLending> borrowingBookListPendingApproval = borrowBookLendingList.stream()
				.filter(bookLending -> bookLending.getLendingStatus() == 0).collect(Collectors.toList());
		// レンタル中本リスト
		List<BookLending> borrowingBookList = borrowBookLendingList.stream()
				.filter(bookLending -> bookLending.getLendingStatus() != 0).collect(Collectors.toList());

		model.addAttribute("lendingBookListPendingApproval", lendingBookListPendingApproval);
		model.addAttribute("lendingBookList", lendingBookList);
		model.addAttribute("borrowingBookListPendingApproval", borrowingBookListPendingApproval);
		model.addAttribute("borrowingBookList", borrowingBookList);
		return "lend_management";
	}

	/**
	 * 貸出リクエストをキャンセルする.
	 * 
	 * @param bookLendingId 本の貸出状況ID
	 * @param bookId        本ID
	 * @return 貸出管理画面
	 */
	@RequestMapping("/cancel_lending_request")
	public String cancelLendingRequest(Integer bookLendingId, Integer bookId) {
		bookService.cancelLendingBookRequest(bookLendingId, bookId);
		// TODO 貸し手にメール送信
		return "redirect:/to_lend_management";
	}

	/**
	 * 貸出リクエストに対し承認する.
	 * 
	 * @param bookLendingId 本の貸出状況ID
	 * @param bookId        本ID
	 * @return 貸出管理画面
	 */
	@RequestMapping("/approval_lending_request")
	public String approvalLendingRequest(Integer bookLendingId, Integer bookId) {
		bookService.runApprovalLendingBookRequest(bookLendingId, bookId);
		// TODO 借り手にメール送信
		return "redirect:/to_lend_management";
	}

	/**
	 * 本の返却を登録する.
	 * 
	 * @param bookLendingId 本の貸出状況ID
	 * @param bookId        本ID
	 * @param updateStatus  更新後の本貸出状況
	 * @return 貸出管理画面
	 */
	@RequestMapping("/confirm_book_return")
	public String confirmBookReturn(Integer bookLendingId, Integer bookId, Integer updateStatus) {
		bookService.confirmBookReturn(bookLendingId, bookId, updateStatus);
		// TODO 借り手にメール送信
		return "redirect:/to_lend_management";
	}

}
