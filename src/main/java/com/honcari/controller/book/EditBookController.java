package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.domain.Category;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.service.book.EditOwnedBookInfoService;
import com.honcari.service.book.FindAllCategoryService;
import com.honcari.service.book.FindByOwnedBookInfoService;

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
		OwnedBookInfo ownedBookInfo = findByOwnedBookInfoService.findByOwnedBookInfoId(ownedBookInfoId);
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
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editBook(Integer ownedBookInfoId, Integer categoryId, String comment, Integer bookStatus, RedirectAttributes redirectAttributes) {
		OwnedBookInfo ownedBookInfo = findByOwnedBookInfoService.findByOwnedBookInfoId(ownedBookInfoId);
		ownedBookInfo.setCategoryId(categoryId);
		ownedBookInfo.setComment(comment);
		ownedBookInfo.setBookStatus(bookStatus);
		editOwnedBookInfoService.editOwnedBookInfo(ownedBookInfo);
		redirectAttributes.addFlashAttribute("successMessage", "書籍情報を変更しました");
		return "redirect:/book/show_mybook";
	}
	
	/**
	 * 書籍情報を削除するメソッド.
	 * 
	 * @param bookId 書籍ID
	 * @return マイブック
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deleteBook(Integer ownedBookInfoId, Model model, RedirectAttributes redirectAttributes) {
		System.out.println(ownedBookInfoId);
		OwnedBookInfo ownedBookInfo = findByOwnedBookInfoService.findByOwnedBookInfoId(ownedBookInfoId);
		if(ownedBookInfo.getBookStatus() == 2) {
			model.addAttribute("errorMessage", "貸し出し承認待ち中の書籍です");
			return showEditBook(model, ownedBookInfoId);
		}
		if(ownedBookInfo.getBookStatus() == 3) {
			model.addAttribute("errorMessage", "貸し出し中の書籍です");
			return showEditBook(model, ownedBookInfoId);
		}
		ownedBookInfo.setBookStatus(4);
		editOwnedBookInfoService.editOwnedBookInfo(ownedBookInfo);
		redirectAttributes.addFlashAttribute("successMessage", "書籍の登録を削除しました");
		return "redirect:/book/show_mybook";
	}
}