package com.honcari.service.book;

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
public class SearchBookService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findByGroupAndTitle(Integer groupId, String title) {
		return userRepository.findByGroupAndTitle(groupId, title);
	}
}
