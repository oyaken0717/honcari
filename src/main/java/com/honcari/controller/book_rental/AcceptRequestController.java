package com.honcari.controller.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	 * @return 貸出情報一覧画面
	 */
	@RequestMapping("/accept")
	public String acceptRequest(Integer bookRentalId) {
		acceptRentalRequestService.acceptRentalRequest(bookRentalId);
		// TODO 借り手にメール送信
		return "redirect:/book_rental/show_list";
	}

}
