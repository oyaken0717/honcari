package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Book;
import com.honcari.domain.Category;
import com.honcari.service.BookService;
import com.honcari.service.book.GetAllCategoryService;

/**
 * 書籍情報を編集するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class EditBookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private GetAllCategoryService getAllCategoryService;
	
	/**
	 * 本編集ページを表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param bookId 本ID
	 * @return　本詳細ページ
	 */
	@RequestMapping("/show_edit_book")
	public String showEditBook(Model model, Integer bookId) {
		Book book = bookService.findByBookId(bookId);
		model.addAttribute("book", book);
		List<Category> categoryList = getAllCategoryService.findAll();
		model.addAttribute("categoryList", categoryList);
		return "book/edit_book";
	}
	
	/**
	 * 書籍情報を編集するメソッド.
	 * 
	 * @param bookId 書籍ID
	 * @param categoryId カテゴリID
	 * @param comment コメント
	 * 
	 * @return マイブック
	 */
	@RequestMapping("/edit_book")
	public String editBook(Integer bookId, Integer categoryId, String comment) {
		bookService.editBook(bookId, categoryId, comment);
		return "redirect:/show_my_book";
	}
	
	/**
	 * 書籍情報を削除するメソッド.
	 * 
	 * @param bookId 書籍ID
	 * @return マイブック
	 */
	@RequestMapping("/delete_book")
	public String deleteBook(Integer bookId) {
		bookService.deleteBook(bookId);
		return "redirect:/show_my_book";
	}
}