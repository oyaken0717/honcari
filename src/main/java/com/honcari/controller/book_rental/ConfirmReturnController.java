package com.honcari.controller.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	 * 本の返却を確認する.
	 * 
	 * @param bookRentalId 貸出情報ID
	 * @param updateStatus 貸出状況
	 * @param loginUser    ログインユーザー
	 * @return 貸出管理画面
	 */
	@RequestMapping(value = "/confirm_return", method = RequestMethod.POST)
	public String confirmReturn(Integer bookRentalId, Integer updateStatus,
			@AuthenticationPrincipal LoginUser loginUser) {
		String processingUserName = loginUser.getUser().getName();
		confirmReturnService.confirmReturn(bookRentalId, updateStatus, processingUserName);
		// TODO 借り手にメール送信
		return "redirect:/book_rental/show_list";
	}

}
