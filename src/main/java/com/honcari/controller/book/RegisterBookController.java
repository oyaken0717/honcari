package com.honcari.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.domain.Category;
import com.honcari.domain.GoogleBooks;
import com.honcari.domain.VolumeInfo;
import com.honcari.form.SearchGoogleBookForm;
import com.honcari.service.book.FindAllCategoryService;
import com.honcari.service.book.GoogleBookApiService;

/**
 * 書籍情報を登録するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@CommonAttribute
@RequestMapping("/book")
public class RegisterBookController {

	@Autowired
	private FindAllCategoryService getAllCategoryService;
	
	@Autowired
	private GoogleBookApiService googleBookApiService;
	
	@ModelAttribute
	private SearchGoogleBookForm setUpForm() {
		return new SearchGoogleBookForm();
	}
	
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
	public String getGoogleBooks(@Validated SearchGoogleBookForm searchGoogleBookForm, BindingResult result, Model model) {
		
		//検索ワードが入力されていない場合エラーを発生させる
		if(result.hasErrors()) {
			return showRegisterBook(model);
		}
		
		//現在のページを取得する
		Integer pageNumber = searchGoogleBookForm.getPageNumber();
		if(pageNumber == null) {
			pageNumber = 1;
		}
		
		//GoogleBooksAPIより書籍情報を取得し、リクエストスコープに格納する(検索結果の0件目から(pageNumber - 1)*17件を飛ばして15件取得する)
		//TODO 計算おかしい…？
		GoogleBooks googleBooks = googleBookApiService.getBook(searchGoogleBookForm.getSearchWord(), pageNumber - 1);
		List<VolumeInfo> volumeInfoList = googleBooks.getItems();
		if(volumeInfoList == null) {
			model.addAttribute("nothing", "書籍が見つかりませんでした。");
		}else {
			model.addAttribute("volumeInfoList", volumeInfoList);
		}
		List<Category> categoryList = getAllCategoryService.findAll();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("searchWord", searchGoogleBookForm.getSearchWord());
		model.addAttribute("nowPageNumber", pageNumber);
		return "book/register_book";
	}
}