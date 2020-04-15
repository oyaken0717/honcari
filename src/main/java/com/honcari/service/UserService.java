package com.honcari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.repository.UserRepository;

/**
 * ユーザー情報に関するサービスクラス.
 * 
 * @author yamadadai
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * グループIDからユーザー情報を取得するメソッド
	 * 
	 * @param groupId グループID
	 * @return ユーザー情報リスト
	 */
	public List<User> findByGroupId(Integer groupId) {
		return userRepository.findByGroupId(groupId);
	}
}
