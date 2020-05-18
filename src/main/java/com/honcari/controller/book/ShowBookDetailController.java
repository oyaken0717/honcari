package com.honcari.controller.book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.LoginUser;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.form.RentalRequestForm;
import com.honcari.service.book.ShowBookDetailService;

/**
 * 本の詳細ページを表示するコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@RequestMapping("/book")
public class ShowBookDetailController {
	
	@Autowired
	private ShowBookDetailService showBookDetailService;
	
	@ModelAttribute
	public RentalRequestForm setUpForm() {
		RentalRequestForm form = new RentalRequestForm();
		LocalDate defaultBeginning = LocalDate.now();
		LocalDate defaultDeadline = defaultBeginning.plusWeeks(2);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		form.setRequestBeginning(formatter.format(defaultBeginning));
		form.setRequestDeadline(formatter.format(defaultDeadline));
		return form;
	}
	
	/**
	 * 本詳細ページを表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param bookId 本ID
	 * @param loginUser ログインユーザー
	 * @return　本詳細ページ
	 */
	@RequestMapping("/show_detail")
	public String showBookDetail(Model model, Integer ownedBookInfoId, @AuthenticationPrincipal LoginUser loginUser) {
		OwnedBookInfo ownedBookInfo = showBookDetailService.searchByOwnedBookId(ownedBookInfoId);
		if(ownedBookInfo.getUserId() == loginUser.getUser().getUserId()) {
			return "error/500";
		}
		model.addAttribute("ownedBookInfo", ownedBookInfo);
		model.addAttribute("book", ownedBookInfo.getBook());
		return "book/book_detail";
	}
	
	
}
