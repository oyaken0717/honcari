package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.Book;
import com.honcari.domain.Category;
import com.honcari.domain.LoginUser;
import com.honcari.form.RegisterBookForm;
import com.honcari.service.book.GetAllCategoryService;
import com.honcari.service.book.GetByIsbnIdService;
import com.honcari.service.book.RegisterBookService;

/**
 * 書籍情報を登録するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class RegisterBookController {

	@Autowired
	private RegisterBookService registerBookService;

	@Autowired
	private GetAllCategoryService getAllCategoryService;
	
	@Autowired
	private GetByIsbnIdService getByIsbnIdService;
	
	/**
	 * 書籍登録画面を表示する.
	 * 
	 * @return 書籍登録画面
	 */
	@RequestMapping("/show_register_book")
	public String showRegisterBook(Model model) {
		List<Category> categoryList = getAllCategoryService.findAll();
		model.addAttribute("categoryList", categoryList);
		return "/register_book";
	}
	
	/**
	 * 書籍登録画面で送られたリクエストパラメータ情報をbooksテーブルに登録する
	 * 
	 * @param registerBookForm 書籍登録画面で送られたリクエストパラメータ
	 * @param loginUser ログインしているユーザ情報
	 * @param redirectAttributes リダイレクト先へリクエストスコープを格納する
	 * @return 書籍登録画面
	 */
	@RequestMapping("/register_book")
	public String registerBook(RegisterBookForm registerBookForm, @AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {
		//booksテーブルに既に該当の書籍が登録されているかISBNコードを用いて検索する
		List<Book> bookList = getByIsbnIdService.getByIsbnId(registerBookForm.getIsbnId());
		
		//検索出来ない場合、書籍情報をbooksテーブルに挿入する
		if(bookList.isEmpty()) {
			Book book = new Book();
			Integer pageCount = null;
			if(registerBookForm.getPageCount().equals("undefined")) {
				pageCount = 0; //TODO テーブル側でdefault値入れてnullでも対応出来るようにする？
			}else {
				pageCount = Integer.parseInt(registerBookForm.getPageCount());
			}
			book.setPageCount(pageCount);
			BeanUtils.copyProperties(registerBookForm, book);
			registerBookService.registerBook(book);
		}
		redirectAttributes.addFlashAttribute("check", "check");
		return "redirect:show_register_book";
	}
}