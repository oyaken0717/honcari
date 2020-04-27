package com.honcari.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.honcari.domain.User;
import com.honcari.repository.UserRepository;

/**
 * mybookを表示するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class ShowMyBookService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザidにてユーザ情報を取得するメソッド
	 * 
	 * @param userId ユーザid
	 * @return ユーザidに一致したユーザ情報
	 */
	public User ShowMyAllBook(Integer userId) {
		return userRepository.findByUserId(userId);
	}
	/**
	 * ユーザidとカテゴリidにてユーザ情報を取得するメソッド.
	 * 
	 * @param userId ユーザ情報
	 * @param categoryId カテゴリid
	 * @return ユーザidとカテゴリidに一致したユーザ情報
	 */
	public List<User> findByCategoryId(Integer userId, Integer categoryId){
		return userRepository.findByCategoryId(userId, categoryId);
	}
}