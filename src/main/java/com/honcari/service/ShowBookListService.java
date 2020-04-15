package com.honcari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.repository.UserRepository;

/**
 * 本一覧を表示させるサービスクラス.
 * 
 * @author yamadadai
 *
 */
@Service
@Transactional
public class ShowBookListService {

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
