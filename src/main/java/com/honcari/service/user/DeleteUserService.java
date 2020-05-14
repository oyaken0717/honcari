package com.honcari.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.repository.BookRentalRepository;
import com.honcari.repository.GroupRelationRepository;
import com.honcari.repository.OwnedBookInfoRepository;
import com.honcari.repository.UserRepository;

/**
 * ユーザ情報を削除するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class DeleteUserService {

	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;
	
	@Autowired
	private BookRentalRepository bookRentalRepository;
	
	/**
	 * ユーザ情報を削除するメソッド.
	 * 
	 * @param userId ユーザid
	 */
	public void deleteUser(Integer userId) {
		groupRelationRepository.delete(userId);
		bookRentalRepository.delete(userId);
		ownedBookInfoRepository.delete(userId);
		userRepository.delete(userId);
	}
}