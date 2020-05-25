package com.honcari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.CustomControllerAdvice.CommonAttribute;

/**
 * アプリの使い方ページを表示するコントローラ.
 * 
 * @author shumpei
 *
 */
@Controller
@CommonAttribute
@RequestMapping("")
public class ShowTutorialController {
	
	/**
	 * アプリの使い方ページを表示する.
	 * 
	 * @return 使い方ページ
	 */
	@RequestMapping(value = "/tutorial")
	public String showTutorial() {
		return "tutorial";
	}

}
