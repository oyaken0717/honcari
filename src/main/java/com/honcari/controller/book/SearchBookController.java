package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.Category;
import com.honcari.domain.LoginUser;
import com.honcari.service.book.SearchBookService;

/**
 * あいまい検索機能のコントローラ.
 * 
 * @author yamadadai
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/book")
public class SearchBookController {

	@Autowired
	private SearchBookService searchBookService;
	
	@RequestMapping("/search")
	public String searchBook(@AuthenticationPrincipal LoginUser loginUser, String title, Model model) {
		List<Category> categoryList = searchBookService.findByUserIdAndTitle(loginUser.getUser().getUserId(), title);
		model.addAttribute("categoryList", categoryList);
		return "book/book_list";
	}
}
