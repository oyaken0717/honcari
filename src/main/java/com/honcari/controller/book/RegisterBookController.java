package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Category;
import com.honcari.domain.GoogleBooks;
import com.honcari.domain.VolumeInfo;
import com.honcari.service.book.FindAllCategoryService;
import com.honcari.service.book.GoogleBookApiService;

/**
 * 書籍情報を登録するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@RequestMapping("/book")
public class RegisterBookController {


	@Autowired
	private FindAllCategoryService getAllCategoryService;
	
	@Autowired
	private GoogleBookApiService googleBookApiService;
	
	/**
	 * 書籍登録画面を表示する.
	 * 
	 * @return 書籍登録画面
	 */
	@RequestMapping("/show_register")
	public String showRegisterBook(Model model) {
		List<Category> categoryList = getAllCategoryService.findAll();
		model.addAttribute("categoryList", categoryList);
		return "book/register_book";
	}
	
	@RequestMapping("/search_books")
	public String getGoogleBooks(String name, Model model) {
		GoogleBooks googleBooks = googleBookApiService.getBook(name);
		List<VolumeInfo> volumeInfoList = googleBooks.getItems();
		model.addAttribute("volumeInfoList", volumeInfoList);
		List<Category> categoryList = getAllCategoryService.findAll();
		model.addAttribute("categoryList", categoryList);
		return "book/register_book";
	}
	
//TODO 非同期処理にて実装したため、コメントアウト　あとで消します
//	/**
//	 * 書籍登録画面で送られたリクエストパラメータ情報をowned_book_infoテーブルに登録する
//	 * 
//	 * @param registerBookForm 書籍登録画面で送られたリクエストパラメータ
//	 * @param loginUser ログインしているユーザ情報
//	 * @param redirectAttributes リダイレクト先へリクエストスコープを格納する
//	 * @return 書籍登録画面
//	 */
//	@RequestMapping(value="/register", method=RequestMethod.POST)
//	public String registerBook(RegisterBookForm registerBookForm, @AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {
//		Integer bookId = null;
//		Book book = new Book();
//		OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
//		
//		//booksテーブルに既に該当の書籍が登録されているかISBNコードを用いて検索する
//		List<Book> bookList = findByIsbnIdService.getByIsbnId(registerBookForm.getIsbnId());
//		
//		//検索出来ない場合、書籍情報をbooksテーブルに挿入しbook_idを戻り値として取得しbookIdに代入する
//		//検索出来た場合、bookList0番目のbook_idを取得しbookIdに代入する
//		if(bookList.isEmpty()) {
//			Integer pageCount = null;
//			if(registerBookForm.getPageCount().equals("undefined")) {
//				pageCount = 0; //TODO テーブル側でdefault値入れてnullでも対応出来るようにする？
//			}else {
//				pageCount = Integer.parseInt(registerBookForm.getPageCount());
//			}
//			book.setIsbnId(registerBookForm.getIsbnId());
//			book.setTitle(registerBookForm.getTitle());
//			book.setAuthor(registerBookForm.getAuthor());
//			book.setPublishedDate(registerBookForm.getPublishedDate());
//			book.setDescription(registerBookForm.getDescription());
//			book.setPageCount(pageCount);
//			book.setThumbnailPath(registerBookForm.getThumbnailPath());
//			Book registeredBook = registerBookService.registerBook(book);
//			bookId = registeredBook.getBookId();
//		}else {
//			bookId = bookList.get(0).getBookId();
//		}
//		ownedBookInfo.setUserId(loginUser.getUser().getUserId());
//		ownedBookInfo.setBookId(bookId);
//		ownedBookInfo.setCategoryId(Integer.parseInt(registerBookForm.getCategoryId()));
//		ownedBookInfo.setBookStatus(1);
//		ownedBookInfo.setComment(registerBookForm.getComment());
//		registerOwnedBookInfoService.registerOwnedBookInfo(ownedBookInfo);
//		redirectAttributes.addFlashAttribute("check", "check");
//		return "redirect:/book/show_register";
//	}
}