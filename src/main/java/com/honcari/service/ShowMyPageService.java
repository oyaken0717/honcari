package com.honcari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.repository.UserRepository;

@Service
@Transactional
public class ShowMyPageService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User showUserInfo(Integer userId) {
		return userRepository.findUserInfoByUserId(userId);
	}

}
