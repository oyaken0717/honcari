package com.honcari.controller.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.service.book_rental.ConfirmReturnService;

/**
 * 本の返却を確認する.
 * 
 * @author shumpei
 *
 */
@RequestMapping("/book_rental")
@Controller
public class ConfirmReturnController {
	
	@Autowired
	private ConfirmReturnService confirmReturnService;
	
	/**
	 * 本の返却を確認する.
	 * 
	 * @param bookRentalId 貸出情報ID
	 * @param updateStatus　貸出状況
	 * @return　貸出管理画面
	 */
	@RequestMapping("/confirm_return")
	public String confirmReturn(Integer bookRentalId, Integer updateStatus) {
		confirmReturnService.confirmReturn(bookRentalId, updateStatus);
		// TODO 借り手にメール送信
		return "redirect:/book_rental/rental_list";
	}

}
