package com.honcari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.form.RegisterUserForm;
import com.honcari.repository.UserRepository;

@Service
@Transactional
public class RegisterUserService {
	
	@Autowired
	private UserRepository userRepository;

	public void registerUser(RegisterUserForm form) {
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setPassword(form.getPassword());
		user.setImagePath(null);
		user.setProfile(null);
		userRepository.insert(user);
	}

	public User checkUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
