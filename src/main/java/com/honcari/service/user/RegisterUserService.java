package com.honcari.service.user;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void registerUser(RegisterUserForm form) {
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setImagePath(null);
		user.setProfile(null);
		user.setUpdatePasswordDate(new Timestamp(System.currentTimeMillis()));
		userRepository.insert(user);
	}

	public User checkUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User checkUserByName(String name) {
		return userRepository.findByName(name);
	}
	
}
