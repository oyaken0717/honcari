package com.honcari.service.user;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.form.RegisterUserForm;
import com.honcari.repository.UserRepository;

/**
 * ユーザー登録に使用するサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザー登録処理.
	 * 
	 * @param form ユーザー登録フォーム
	 */
	public void registerUser(RegisterUserForm form) {
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setImagePath(null);
		user.setProfile(null);
		user.setUpdatePasswordDate(new Timestamp(System.currentTimeMillis()));
		userRepository.insert(user);
	}

	/**
	 * メールアドレス重複チェックメソッド.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User checkUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * ユーザー名重複存在チェック
	 * 
	 * @param name
	 * @return ユーザー情報
	 */
	public User checkUserByName(String name) {
		return userRepository.findByName(name);
	}

}
