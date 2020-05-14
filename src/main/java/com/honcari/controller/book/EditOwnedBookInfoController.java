package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.common.BookStatusEnum;
import com.honcari.domain.Category;
import com.honcari.domain.OwnedBookInfo;
import com.honcari.form.EditOwnedBookInfoForm;
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
public class EditOwnedBookInfoController {

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
	public String editBook(@Validated EditOwnedBookInfoForm editOwnedBookInfoForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		//書籍情報をowned_book_infoテーブルから取得する
		OwnedBookInfo ownedBookInfo = findByOwnedBookInfoService.findByOwnedBookInfoId(editOwnedBookInfoForm.getOwnedBookInfoId());
		
		//編集されたカテゴリid, コメント, 書籍状況を取得していたownedBookInfoにセットする
		ownedBookInfo.setCategoryId(editOwnedBookInfoForm.getCategoryId());
		ownedBookInfo.setComment(editOwnedBookInfoForm.getComment());
		ownedBookInfo.setBookStatus(editOwnedBookInfoForm.getBookStatus());
		
		//書籍情報編集画面に遷移した時点でのversionをセットする
		ownedBookInfo.setVersion(editOwnedBookInfoForm.getVersion());
		
		//owned_book_infoテーブルを更新する
		//更新時にversionの値が不一致の場合例外処理を行う(EditOwnedBookInfoServiceにてtry-catch)
		try {
			editOwnedBookInfoService.editOwnedBookInfo(ownedBookInfo);
		}catch(Exception ex) {
			ex.printStackTrace();
			model.addAttribute("errorMessage", "書籍情報の更新に失敗しました");
			return showEditBook(model, ownedBookInfo.getOwnedBookInfoId());
		}
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
	public String deleteBook(Integer ownedBookInfoId, Integer version, Model model, RedirectAttributes redirectAttributes) {
		
		//書籍情報をowned_book_infoテーブルから取得する
		OwnedBookInfo ownedBookInfo = findByOwnedBookInfoService.findByOwnedBookInfoId(ownedBookInfoId);
		
		//ownedBookInfoのbookStatusが貸し出し承認待ち、もしくは貸し出し中だった場合は削除処理を行わず編集画面に戻す
		if(ownedBookInfo.getBookStatus() == BookStatusEnum.BEFORE_LENDING.getValue()) {
			model.addAttribute("errorMessage", "貸出承認待ちの書籍です");
			return showEditBook(model, ownedBookInfoId);
		}
		if(ownedBookInfo.getBookStatus() == BookStatusEnum.LENDING.getValue()) {
			model.addAttribute("errorMessage", "貸出中の書籍です");
			return showEditBook(model, ownedBookInfoId);
		}
		
		//ownedBookInfoのbookStatusを削除に変更し更新処理を行う
		ownedBookInfo.setBookStatus(BookStatusEnum.DELETE.getValue());
		ownedBookInfo.setVersion(version);
		editOwnedBookInfoService.editOwnedBookInfo(ownedBookInfo);
		redirectAttributes.addFlashAttribute("successMessage", "書籍の登録を削除しました");
		return "redirect:/book/show_mybook";
	}
}