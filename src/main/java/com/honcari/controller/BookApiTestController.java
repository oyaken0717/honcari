package com.honcari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookApiTestController {

	@RequestMapping("")
	public String test() {
		return "api_test";
	}
}
