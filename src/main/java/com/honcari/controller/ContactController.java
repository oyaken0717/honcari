package com.honcari.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honcari.CustomControllerAdvice.CommonAttribute;
import com.honcari.form.ContactForm;
import com.honcari.service.SendContactMailService;

/**
 * お問い合わせ用のコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@CommonAttribute
public class ContactController {
	
	@Autowired
	private SendContactMailService sendContactMailService;
	
	@ModelAttribute
	public ContactForm setUpContactForm() {
		return new ContactForm();
	}
	
	/**
	 * お問い合わせフォームへ遷移するメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @param loginUser ログイン中のユーザー
	 * @return お問い合わせ画面
	 */
	@RequestMapping("/show_contact")
	public String showContact() {
		return "contact";
	}
	
	/**
	 * お問い合わせを送信するメソッド.
	 * 
	 * @param contactForm お問い合わせ用フォーム
	 * @param result エラー格納オブジェクト
	 * @param model リクエストスコープ
	 * @param loginUser ログイン中のユーザー
	 * @return お問い合わせ画面
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String contact(@Validated ContactForm contactForm, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			return showContact();
		}
		sendContactMailService.sendContactMailToCustomer(contactForm);
		redirectAttributes.addFlashAttribute("successMessage", "お問い合わせを送信しました！");
		return "redirect:/show_contact";
	}

}
