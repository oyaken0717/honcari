package com.honcari.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.form.RegisterUserForm;

@Service
@Transactional
public class RegisterUserService {

	public void registerUser(RegisterUserForm form) {
		
	}

	public User checkUserByEmail(String email) {
		return null;
	}
}
