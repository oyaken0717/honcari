package com.honcari.service.user;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

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
	
    private String User_Folder_Name = System.getenv("AWS_USER_FOLDER_NAME");
	private String Bucket_Name = System.getenv("AWS_BUCKET_NAME");

	/**
	 * ユーザー登録処理.
	 * 
	 * @param form ユーザー登録フォーム
	 */
	public void registerUser(RegisterUserForm form,HttpServletRequest request) {
		if (!request.getHeader("REFERER").contains("heroku")) {
			User_Folder_Name = "profile-image-test";
			Bucket_Name = "honcari-image-test";
		}
		String profileImage = "https://"+Bucket_Name+".s3-ap-northeast-1.amazonaws.com/"+User_Folder_Name+"/user_default.png";
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setImagePath(profileImage);
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
