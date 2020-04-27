package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Category;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.service.book.EditOwnedBookInfoService;
import com.honcari.service.book.FindByOwnedBookInfoService;
import com.honcari.service.book.FindAllCategoryService;

/**
 * 書籍情報を編集するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@RequestMapping("/book")
public class EditBookController {

	@Autowired
	private EditOwnedBookInfoService editOwnedBookInfoService;
	
	@Autowired
	private FindAllCategoryService findAllCategoryService;
	
	@Autowired
	private FindByOwnedBookInfoService findByOwnedBookInfoService;
	
	
	/**
	 * 書籍編集ページを表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param ownedBookInfoId ユーザが所有している書籍情報
	 * @return 書籍編集ページ
	 */
	@RequestMapping("/show_edit")
	public String showEditBook(Model model, Integer ownedBookInfoId) {
		OwnedBookInfo ownedBookInfo = new OwnedBookInfo();
		model.addAttribute("ownedBookInfo", ownedBookInfo);
		List<Category> categoryList = findAllCategoryService.findAll();
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
	@RequestMapping("/edit")
	public String editBook(Integer ownedBookInfoId, Integer categoryId, String comment) {
		OwnedBookInfo ownedBookInfo = findByOwnedBookInfoService.findByOwnedBookInfoId(ownedBookInfoId);
		ownedBookInfo.setCategoryId(categoryId);
		ownedBookInfo.setComment(comment);
		editOwnedBookInfoService.editOwnedBookInfo(ownedBookInfo);
		return "redirect:/book/show_mybook";
	}
	
	/**
	 * 書籍情報を削除するメソッド.
	 * 
	 * @param bookId 書籍ID
	 * @return マイブック
	 */
	@RequestMapping("/delete")
	public String deleteBook(Integer ownedBookInfoId) {
		OwnedBookInfo ownedBookInfo = findByOwnedBookInfoService.findByOwnedBookInfoId(ownedBookInfoId);
		ownedBookInfo.setBookStatus(4);
		editOwnedBookInfoService.editOwnedBookInfo(ownedBookInfo);
		return "redirect:/book/show_mybook";
	}
}