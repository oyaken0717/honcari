package com.honcari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;

/**
 * FAQ画面を表示するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@CommonAttribute
public class ShowFaqController {

	/**
	 * FAQ画面を表示させるメソッド.
	 * 
	 * @return FAQ画面
	 */
	@RequestMapping("/show_faq")
	public String showFaq() {
		return "faq";
	}
}
