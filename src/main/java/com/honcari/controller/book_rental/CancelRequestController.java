package com.honcari.controller.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	 * @return 貸出情報一覧画面
	 */
	@RequestMapping("/cancel")
	public String cancelRequest(Integer bookRentalId) {
		cancelRentalRequestService.cancelRentalRequest(bookRentalId);
		// TODO 貸し手にメール送信
		return "redirect:/book_rental/show_list";
	}

}
