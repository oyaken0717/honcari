//package com.honcari.controller;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * @author yamaseki
// *
// */
//@Controller
//@RequestMapping("")
//public class ReturnPageController {
//	
//	@Autowired
//	private HttpSession session;
//	
//	@RequestMapping("/return_page")
//	public String returnPage() {
//		
//		return "redirect:"+(String) session.getAttribute("referer");
//	}
//
//}
