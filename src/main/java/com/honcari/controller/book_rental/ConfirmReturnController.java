package com.honcari.controller.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.LoginUser;
import com.honcari.service.book_rental.ConfirmReturnService;

/**
 * 本の返却を確認する.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("/book_rental")
public class ConfirmReturnController {

	@Autowired
	private ConfirmReturnService confirmReturnService;

	/**
	 * @param loginUser            ログインユーザー
	 * @param bookRentalId         貸出情報ID
	 * @param updateStatus         貸出状況
	 * @param bookRentalVersion    貸出情報バージョン
	 * @param ownedBookInfoVersion 所有本情報バージョン
	 * @param redirectAttributes
	 * @return 貸出中の本一覧画面
	 */
	@RequestMapping(value = "/confirm_return", method = RequestMethod.POST)
	public String confirmReturn(@AuthenticationPrincipal LoginUser loginUser, Integer bookRentalId,
			Integer updateStatus, Integer bookRentalVersion, Integer ownedBookInfoVersion,
			RedirectAttributes redirectAttributes) {
		String processingUserName = loginUser.getUser().getName();

		try {
			confirmReturnService.confirmReturn(bookRentalId, updateStatus, processingUserName, bookRentalVersion,
					ownedBookInfoVersion);
			redirectAttributes.addFlashAttribute("successMessage", "本の返却を確認しました！");
		} catch (Exception ex) {
			ex.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "本の返却確認に失敗しました！");
		}

		return "redirect:/book_rental/show_list";
	}

}
