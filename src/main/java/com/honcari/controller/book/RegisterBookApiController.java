package com.honcari.controller.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.Book;
import com.honcari.domain.LoginUser;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.form.RegisterBookForm;
import com.honcari.service.book.FindByIsbnIdService;
import com.honcari.service.book.RegisterBookService;
import com.honcari.service.book.RegisterOwnedBookInfoService;

@RestController
@RequestMapping("/book_api")
public class RegisterBookApiController {

	@Autowired
	private RegisterBookService registerBookService;

	@Autowired
	private FindByIsbnIdService findByIsbnIdService;
	
	@Autowired
	private RegisterOwnedBookInfoService registerOwnedBookInfoService;
	
	/**
	 * 書籍登録画面で送られたリクエストパラメータ情報をowned_book_infoテーブルに登録する
	 * 
	 * @param registerBookForm 書籍登録画面で送られたリクエストパラメータ
	 * @param loginUser ログインしているユーザ情報
	 * @param redirectAttributes リダイレクト先へリクエストスコープを格納する
	 * @return 書籍登録画面
	 */
	@ResponseBody
	@RequestMapping(value="/register_book", method=RequestMethod.POST)
	public Map<String, String> registerBook(RegisterBookForm registerBookForm, @AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {
		Integer bookId = null;
		Book book = new Book();
		OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
		Map<String, String> map = new HashMap<>();
		System.out.println(registerBookForm);
		
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
			book.setIsbnId(registerBookForm.getIsbnId());
			book.setTitle(registerBookForm.getTitle());
			book.setAuthor(registerBookForm.getAuthor());
			book.setPublishedDate(registerBookForm.getPublishedDate());
			book.setDescription(registerBookForm.getDescription());
			book.setPageCount(pageCount);
			book.setThumbnailPath(registerBookForm.getThumbnailPath());
			Book registeredBook = registerBookService.registerBook(book);
			bookId = registeredBook.getBookId();
		}else {
			bookId = bookList.get(0).getBookId();
		}
		ownedBookInfo.setUserId(loginUser.getUser().getUserId());
		ownedBookInfo.setBookId(bookId);
		ownedBookInfo.setCategoryId(Integer.parseInt(registerBookForm.getCategoryId()));
		ownedBookInfo.setBookStatus(1);
		ownedBookInfo.setComment(registerBookForm.getComment());
		registerOwnedBookInfoService.registerOwnedBookInfo(ownedBookInfo);
		map.put("check", "書籍情報の登録が完了しました");
		return map;
	}
}