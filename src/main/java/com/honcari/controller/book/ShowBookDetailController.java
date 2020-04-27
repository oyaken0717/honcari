package com.honcari.controller.book;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
@Controller
@RequestMapping("/book")
public class ShowBookDetailController {
	
	@Autowired
	private ShowBookDetailService showBookDetailService;
	
	@ModelAttribute
	public RentalRequestForm setUpForm() {
		RentalRequestForm form = new RentalRequestForm();
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.WEEK_OF_MONTH, 2);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		form.setDeadline(sdf.format(date));
		return form;
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
		return "book/book_detail";
	}
	
	
}
