package com.honcari.service;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.form.EditUserForm;
import com.honcari.repository.UserRepository;

/**
 * ユーザー情報を編集するサービス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class EditUserService {
	
	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	/**
	 * メールアドレスが登録済か否かを確認するメソッド.
	 * 
	 * @param email メールアドレス
	 * @return 登録済であればtrue、未登録であればfalse
	 */
	public boolean isExistOtherUserByEmail(EditUserForm editUserForm) {
		User user = userRepository.findByEmail(editUserForm.getEmail());
		if(Objects.isNull(user)) {
			return false;
		}
		if(user.getId().equals(editUserForm.getId())) {
			return false;
		}
		return true;
	}
	
	/**
	 * ユーザー情報を編集するメソッド.
	 * 
	 * @param editUserForm ユーザー情報編集用フォーム
	 */
	public void editUser(EditUserForm editUserForm) {
		User user = new User();
		BeanUtils.copyProperties(editUserForm, user);
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if(editUserForm.getPassword().isEmpty()) {
			user.setPassword(editUserForm.getDefaultPassword());
		}
		userRepository.update(user);
	}

}
