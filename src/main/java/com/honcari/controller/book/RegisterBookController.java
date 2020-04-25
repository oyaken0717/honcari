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
import com.honcari.domain.OwnedBookInfo;
import com.honcari.form.RegisterBookForm;
import com.honcari.service.book.FindAllCategoryService;
import com.honcari.service.book.FindByIsbnIdService;
import com.honcari.service.book.RegisterBookService;
import com.honcari.service.book.RegisterOwnedBookInfoService;

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
	private FindAllCategoryService getAllCategoryService;
	
	@Autowired
	private FindByIsbnIdService findByIsbnIdService;
	
	@Autowired
	private RegisterOwnedBookInfoService registerOwnedBookInfoService;
	
	/**
	 * 書籍登録画面を表示する.
	 * 
	 * @return 書籍登録画面
	 */
	@RequestMapping("/show_register_book")
	public String showRegisterBook(Model model) {
		List<Category> categoryList = getAllCategoryService.findAll();
		model.addAttribute("categoryList", categoryList);
		return "book/register_book";
	}
	
	/**
	 * 書籍登録画面で送られたリクエストパラメータ情報をowned_book_infoテーブルに登録する
	 * 
	 * @param registerBookForm 書籍登録画面で送られたリクエストパラメータ
	 * @param loginUser ログインしているユーザ情報
	 * @param redirectAttributes リダイレクト先へリクエストスコープを格納する
	 * @return 書籍登録画面
	 */
	@RequestMapping("/register_book")
	public String registerBook(RegisterBookForm registerBookForm, @AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {
		Integer bookId = null;
		Book book = new Book();
		OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
		//booksテーブルに既に該当の書籍が登録されているかISBNコードを用いて検索する
		List<Book> bookList = findByIsbnIdService.getByIsbnId(registerBookForm.getIsbnId());
		
		//検索出来ない場合、書籍情報をbooksテーブルに挿入しbook_idを戻り値として取得しbookIdに代入する
		//検索出来た場合、bookList0番目のbook_idを取得しbookIdに代入する
		if(bookList.isEmpty()) {
			Integer pageCount = null;
			if(registerBookForm.getPageCount().equals("undefined")) {
				pageCount = 0; //TODO テーブル側でdefault値入れてnullでも対応出来るようにする？
			}else {
				pageCount = Integer.parseInt(registerBookForm.getPageCount());
			}
			book.setPageCount(pageCount);
			BeanUtils.copyProperties(registerBookForm, book);
			Book registeredBook = registerBookService.registerBook(book);
			bookId = registeredBook.getBookId();
		}else {
			bookId = bookList.get(0).getBookId();
		}
		ownedBookInfo.setUserId(loginUser.getUser().getId());
		ownedBookInfo.setBookId(bookId);
		ownedBookInfo.setCategoryId(Integer.parseInt(registerBookForm.getCategoryId()));
		ownedBookInfo.setBookStatus(1);
		ownedBookInfo.setComment(registerBookForm.getComment());
		registerOwnedBookInfoService.registerOwnedBookInfo(ownedBookInfo);
		redirectAttributes.addFlashAttribute("check", "check");
		return "redirect:/show_register_book";
	}
}