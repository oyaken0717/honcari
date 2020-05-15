package com.honcari.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.honcari.domain.BookRental;
import com.honcari.domain.Group;
import com.honcari.domain.LoginUser;
import com.honcari.service.book_rental.SearchByBorrowerService;
import com.honcari.service.book_rental.SearchByOwnerService;
import com.honcari.service.group.SearchGroupByUserIdService;
import com.honcari.service.user.DeleteUserService;

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
	private DeleteUserService deleteUserService;

	@Autowired
	private SearchByOwnerService searchByOwnerService;
	
	@Autowired
	private SearchByBorrowerService searchByBorrowerService;
	
	@Autowired
	private SearchGroupByUserIdService searchGroupByUserIdService;
	
	/**
	 * ユーザ情報を削除するメソッド(statusを9に変更する)
	 * 
	 * @param loginUser
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/delete_user",method = RequestMethod.POST)
	public String deleteUser(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = loginUser.getUser().getUserId();
		List<BookRental> bookRentalListByOwner = searchByOwnerService.searchRentalListByOwner(userId);
		List<BookRental> bookRentalListByBorrower = searchByBorrowerService.searchRentalListByBorrower(userId);
		List<Group> groupList = searchGroupByUserIdService.searchGroupByUserId(userId);
		if(bookRentalListByOwner.size() != 0) {
			model.addAttribute("ownerError", "貸し出しリクエスト承認待ち、もしくは貸し出し中の本があります");
		}
		if(bookRentalListByBorrower.size() != 0){
			model.addAttribute("borrowerError", "貸し出しリクエスト送信中、もしくは借りている本があります");
		}
		if(groupList.size() != 0) {
			model.addAttribute("groupError", "オーナーになっているグループがあります。オーナー権を委任して下さい");
		}
		if(bookRentalListByOwner.size() != 0 || bookRentalListByBorrower.size() != 0 || groupList.size() != 0) {
			return "faq";
		}
		deleteUserService.deleteUser(userId);
		return "redirect:/logout";
	}	
}