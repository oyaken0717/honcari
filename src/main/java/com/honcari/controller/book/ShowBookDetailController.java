package com.honcari.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.OwnedBookInfo;
import com.honcari.form.RentalRequestForm;
import com.honcari.service.book.ShowBookDetailService;

/**
 * 本の詳細ページを表示するコントローラ.
 * 
 * @author shumpei
 *
 */
@RequestMapping("/book")
@Controller
public class ShowBookDetailController {
	
	@Autowired
	private ShowBookDetailService showBookDetailService;
	
	@ModelAttribute
	public RentalRequestForm setUpForm() {
		return new RentalRequestForm();
	}
	
	/**
	 * 本詳細ページを表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param bookId 本ID
	 * @return　本詳細ページ
	 */
	@RequestMapping("/show_detail")
	public String showBookDetail(Model model, Integer ownedBookInfoId) {
		OwnedBookInfo ownedBookInfo = showBookDetailService.searchByOwnedBookId(ownedBookInfoId);
		model.addAttribute("ownedBookInfo", ownedBookInfo);
		model.addAttribute("book", ownedBookInfo.getBook());
		return "book_detail";
	}
	
	
}
