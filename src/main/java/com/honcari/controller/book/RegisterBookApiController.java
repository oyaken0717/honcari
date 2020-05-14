package com.honcari.controller.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.honcari.domain.Book;
import com.honcari.domain.LoginUser;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.form.RegisterBookForm;
import com.honcari.service.book.FindByIsbnIdService;
import com.honcari.service.book.RegisterBookService;
import com.honcari.service.book.RegisterOwnedBookInfoService;

/**
 * 非同期で書籍登録を行うコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@CrossOrigin
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
	 * @return 書籍登録画面
	 */
	@ResponseBody
	@RequestMapping(value="/register_book", method=RequestMethod.POST)
	public Map<String, String> registerBook(RegisterBookForm registerBookForm, @AuthenticationPrincipal LoginUser loginUser) {
		System.out.println(registerBookForm);
		
		Integer bookId = null;
		Book book = new Book();
		OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
		Map<String, String> map = new HashMap<>();
		
		//booksテーブルに既に該当の書籍が登録されているかISBNコードを用いて検索する
		List<Book> bookList = findByIsbnIdService.getByIsbnId(registerBookForm.getIsbnId());
		
		//検索出来ない場合、書籍情報をbooksテーブルに挿入しbook_idを戻り値として取得しbookIdに代入する
		//検索出来た場合、bookList0番目のbook_idを取得しbookIdに代入する
		if(bookList.isEmpty()) {
			String title = null;
			String author = null;
			String publishedDate = null;
			String description = null;
			String pageCount = null;
			String thumbnailPath = null;
			
			if(registerBookForm.getTitle() == null || registerBookForm.getTitle().equals("undefined")) {
				title = "ー";
			}else {
				title = registerBookForm.getTitle();
			}
			
			if(registerBookForm.getAuthor() == null || registerBookForm.getAuthor().equals("undefined")) {
				author = "ー";
			}else {
				author = registerBookForm.getAuthor();
			}
			
			if(registerBookForm.getPublishedDate() == null || registerBookForm.getPublishedDate().equals("undefined")) {
				publishedDate = "ー";
			}else {
				publishedDate = registerBookForm.getPublishedDate();
			}
			
			if(registerBookForm.getDescription() == null || registerBookForm.getDescription().equals("undefined")) {
				description = "ー";
			}else {
				description = registerBookForm.getDescription();
			}
			
			if(registerBookForm.getPageCount() == null || registerBookForm.getPageCount().equals("undefined")) {
				pageCount = "ー";
			}else {
				pageCount = registerBookForm.getPageCount();
			}
			
			if(registerBookForm.getThumbnailPath() == null || registerBookForm.getThumbnailPath().equals("undefined")) {
				thumbnailPath = "ー";
			}else {
				thumbnailPath = registerBookForm.getThumbnailPath();
			}
			
			book.setIsbnId(registerBookForm.getIsbnId());
			book.setTitle(title);
			book.setAuthor(author);
			book.setPublishedDate(publishedDate);
			book.setDescription(description);
			book.setPageCount(pageCount);
			book.setThumbnailPath(thumbnailPath);
			Book registeredBook = registerBookService.registerBook(book);
			bookId = registeredBook.getBookId();
		}else {
			bookId = bookList.get(0).getBookId();
		}
		
		//owned_book_infoテーブルにデータを挿入する
		ownedBookInfo.setUserId(loginUser.getUser().getUserId());
		ownedBookInfo.setBookId(bookId);
		ownedBookInfo.setCategoryId(Integer.parseInt(registerBookForm.getCategoryId()));
		ownedBookInfo.setBookStatus(Integer.parseInt(registerBookForm.getBookStatus()));
		ownedBookInfo.setComment(registerBookForm.getComment());
		registerOwnedBookInfoService.registerOwnedBookInfo(ownedBookInfo);
		map.put("check", "書籍情報の登録が完了しました");
		return map;
	}
}