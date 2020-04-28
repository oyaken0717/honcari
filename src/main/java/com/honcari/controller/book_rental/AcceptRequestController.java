package com.honcari.controller.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.honcari.domain.LoginUser;
import com.honcari.service.book_rental.AcceptRentalRequestService;

/**
 * 貸出申請を承認する.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("/book_rental")
public class AcceptRequestController {

	@Autowired
	private AcceptRentalRequestService acceptRentalRequestService;

	/**
	 * 貸出申請を承認する.
	 * 
	 * @param bookRentalId 貸出申請ID
	 * @param loginUser    ログインユーザー
	 * @return 貸出情報一覧画面
	 */
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	public String acceptRequest(Integer bookRentalId, @AuthenticationPrincipal LoginUser loginUser) {
		String processingUserName = loginUser.getUser().getName();
		acceptRentalRequestService.acceptRentalRequest(bookRentalId, processingUserName);
		// TODO 借り手にメール送信
		return "redirect:/book_rental/show_list";
	}

}
