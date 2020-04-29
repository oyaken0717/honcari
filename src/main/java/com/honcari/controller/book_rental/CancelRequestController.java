package com.honcari.controller.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.LoginUser;
import com.honcari.service.book_rental.CancelRentalRequestService;

/**
 * 貸出申請をキャンセルする.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("/book_rental")
public class CancelRequestController {

	@Autowired
	private CancelRentalRequestService cancelRentalRequestService;

	/**
	 * 貸出申請をキャンセルする.
	 * 
	 * @param bookRentalId 貸出情報ID
	 * @param loginUser    ログインユーザー
	 * @return 貸出情報一覧画面
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancelRequest(Integer bookRentalId, @AuthenticationPrincipal LoginUser loginUser,
			Integer bookRentalVersion, Integer ownedBookInfoVersion, RedirectAttributes redirectAttributes) {
		String processingUserName = loginUser.getUser().getName();
		try {
			cancelRentalRequestService.cancelRentalRequest(bookRentalId, processingUserName, bookRentalVersion,
					ownedBookInfoVersion);
			// TODO 貸し手にメール送信
			redirectAttributes.addFlashAttribute("successMessage", "貸出リクエストをキャンセルしました！");
		} catch (Exception ex) {
			ex.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "貸出リクエストのキャンセルに失敗しました！");
		}
		return "redirect:/book_rental/show_list";
	}

}
