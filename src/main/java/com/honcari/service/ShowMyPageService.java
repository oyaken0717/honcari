package com.honcari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.repository.UserRepository;

/**
 * マイページｊを表示するサービス.
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
	
	/**
	 * ユーザidとカテゴリidにてユーザ情報を取得する.
	 * 
	 * @param userId ユーザ情報
	 * @param categoryId カテゴリid
	 * @return ユーザidとカテゴリidに一致したユーザ情報
	 */
	public User findByCategoryId(Integer userId, Integer categoryId){
		return userRepository.findByCategoryId(userId, categoryId);
	}
}