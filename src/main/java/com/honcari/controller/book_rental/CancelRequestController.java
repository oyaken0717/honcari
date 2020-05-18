package com.honcari.controller.book_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.LoginUser;
import com.honcari.service.book_rental.CancelExtendRequestService;
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

	@Autowired
	private CancelExtendRequestService cancelExtendRequestService;

	/**
	 * 貸出申請をキャンセルする.
	 * 
	 * @param bookRentalId         貸出申請ID
	 * @param loginUser            ログインユーザー
	 * @param rentalStatus         貸出状況
	 * @param bookRentalVersion    貸出状況バージョン
	 * @param ownedBookInfoVersion 所有情報バージョン
	 * @param redirectAttributes   フラッシュスコープ
	 * @return 貸出情報一覧画面
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancelRequest(Integer bookRentalId, @AuthenticationPrincipal LoginUser loginUser,
			Integer rentalStatus, Integer bookRentalVersion, Integer ownedBookInfoVersion,
			RedirectAttributes redirectAttributes) {
		String updateUserName = loginUser.getUser().getName();

		try {
			if (rentalStatus == RentalStatusEnum.WAIT_APPROVAL.getValue()) {
				cancelRentalRequestService.cancelRentalRequest(bookRentalId, updateUserName, bookRentalVersion,
						ownedBookInfoVersion);
			  //延長承認待ちの状態を仮定しているが、実際は存在しないby湯口
			} else if (rentalStatus == RentalStatusEnum.WAIT_EXTEND.getValue()) {
				cancelExtendRequestService.cancelExtendRequest(bookRentalId, updateUserName, bookRentalVersion);
			}
			redirectAttributes.addFlashAttribute("successMessage", "貸出リクエストをキャンセルしました！");
		} catch (Exception ex) {
			ex.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "貸出リクエストのキャンセルに失敗しました！");
		}
		return "redirect:/book_rental/show_pending_list";
	}

}
