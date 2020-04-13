package com.honcari.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ユーザ登録、更新画面の入力したパスワードと確認用パスワードが一致しているか確認するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@RestController
@RequestMapping(value = "/check_password_api")
public class CheckPasswordApiController {

	@ResponseBody
	@RequestMapping(value = "/passwordcheck", method = RequestMethod.POST)
	public Map<String, String> passwordcheck(String password, String confirmationPassword) {
		Map<String, String> map = new HashMap<>();

		// 8文字以上チェック
		final int PASSWORD_MAX_LENGTH = 6;
		String robustnessMessage = null;
		if (password.length() >= PASSWORD_MAX_LENGTH) {
			robustnessMessage = "パスワード入力OK!";
		} else {
			robustnessMessage = "パスワードは" + PASSWORD_MAX_LENGTH + "文字以上で入力してください";
		}
		map.put("robustnessMessage", robustnessMessage);

		// パスワード一致チェック
		String disagreementMessage = null;
		if (password.equals(confirmationPassword)) {
			disagreementMessage = "確認用パスワード入力OK!";
		} else {
			disagreementMessage = "パスワードが一致していません";
		}
		map.put("disagreementMessage", disagreementMessage);		
		return map;
	}
}