package com.honcari.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.repository.UserRepository;

/**
 * ユーザ情報を削除するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class DeleteUserService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザ情報を削除するメソッド.
	 * 
	 * @param userId ユーザid
	 */
	public void deleteUser(Integer userId) {
		userRepository.delete(userId);
	}
}
