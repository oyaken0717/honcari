package com.honcari.controller.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.LoginUser;
import com.honcari.service.book_rental.AcceptExtendRequestService;
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

	@Autowired
	private AcceptExtendRequestService acceptExtendRequestService;

	/**
	 * 貸出申請を承認する.
	 * 
	 * @param bookRentalId         貸出申請ID
	 * @param loginUser            ログインユーザー
	 * @param rentalStatus         貸出状況
	 * @param bookRentalVersion    貸出状況バージョン
	 * @param ownedBookInfoVersion 所有情報バージョン
	 * @param redirectAttributes   フラッシュスコープ
	 * @return 貸出申請管理画面
	 */
	@RequestMapping(value = "/determine", params = "accept",method = RequestMethod.POST)
	public String acceptRequest(Integer bookRentalId, @AuthenticationPrincipal LoginUser loginUser,
			Integer rentalStatus, Integer bookRentalVersion, Integer ownedBookInfoVersion,
			RedirectAttributes redirectAttributes) {
		String updateUserName = loginUser.getUser().getName();
		
		//承認処理を実行
		try {
			if (rentalStatus == RentalStatusEnum.WAIT_APPROVAL.getValue()) {
				acceptRentalRequestService.acceptRentalRequest(bookRentalId, updateUserName, bookRentalVersion, ownedBookInfoVersion);
			  //延長承認待ちの状態を仮定しているが、実際は存在しないby湯口
			} else if (rentalStatus == RentalStatusEnum.WAIT_EXTEND.getValue()) {
				acceptExtendRequestService.acceptExtendRequest(bookRentalId, updateUserName, bookRentalVersion);
			}
			redirectAttributes.addFlashAttribute("successMessage", "貸出リクエストを承認しました！");
		} catch (Exception ex) {
			ex.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "貸出リクエストの承認に失敗しました！");
		}
		return "redirect:/book_rental/show_pending_list";
	}

}
