package com.honcari.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
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

	/**
	 * ユーザー情報を編集するメソッド.
	 * 
	 * @param editUserForm ユーザー情報
	 */
	public void editUser(User user) {
		userRepository.update(user);
	}
}