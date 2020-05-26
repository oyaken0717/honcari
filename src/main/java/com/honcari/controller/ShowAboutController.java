package com.honcari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;

/**
 * アプリの概要ページを表示するコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@CommonAttribute
public class ShowAboutController {
	
	/**
	 * アプリの概要ページを表示する.
	 * 
	 * @return アプリの概要ページ
	 */
	@RequestMapping(value="/about")
	public String showAbout() {
		return "about";
	}

}
