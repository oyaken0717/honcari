package com.honcari.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.BookRental;
import com.honcari.domain.LoginUser;
import com.honcari.domain.User;
import com.honcari.service.book_rental.SearchByBorrowerService;
import com.honcari.service.book_rental.SearchByOwnerService;
import com.honcari.service.user.EditUserService;
import com.honcari.service.user.SearchUserByUserIdService;

/**
 * ユーザ情報を削除するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@RequestMapping("/user")
public class DeleteUserController {
	
	@Autowired
	private SearchUserByUserIdService searchUserByUserIdService;

	@Autowired
	private EditUserService editUserService;
	
	@Autowired
	private SearchByOwnerService searchByOwnerService;
	
	@Autowired
	private SearchByBorrowerService searchByBorrowerService;
	
	/**
	 * ユーザ情報を削除するメソッド(statusを9に変更する)
	 * 
	 * @param loginUser
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete_user")
	public String deleteUser(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = loginUser.getUser().getUserId();
		List<BookRental> bookRentalListByOwner = searchByOwnerService.searchRentalListByOwner(userId);
		List<BookRental> bookRentalListByBorrower = searchByBorrowerService.searchRentalListByBorrower(userId);
		if(bookRentalListByOwner.size() != 0) {
			model.addAttribute("ownerError", "貸し出しリクエスト承認待ち、もしくは貸し出し中の本があります");
		}
		if(bookRentalListByBorrower.size() != 0){
			model.addAttribute("borrowerError", "貸し出しリクエスト送信中、もしくは借りている本があります");
		}
		if(bookRentalListByOwner.size() != 0 || bookRentalListByBorrower.size() != 0) {
			return "faq";
		}
		User user = searchUserByUserIdService.showUser(userId);
		user.setStatus(9);
		editUserService.editUser(user);
		return "redirect:/user/to_login";
	}	
}