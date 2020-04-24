package com.honcari.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.service.BookService;

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