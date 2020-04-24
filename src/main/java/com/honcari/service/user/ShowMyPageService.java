package com.honcari.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.repository.UserRepository;

/**
 * マイページを表示するサービス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class ShowMyPageService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザー情報を取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	public User showUser(Integer userId) {
		return userRepository.findByUserId(userId);
	}
}