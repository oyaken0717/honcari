package com.honcari.service.user;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.form.EditUserForm;
import com.honcari.repository.UserRepository;

/**
 * メールアドレスが登録済か否かを確認するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class SearchExistOtherUserByEmailService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * メールアドレスが登録済か否かを確認するメソッド.
	 * 
	 * @param email メールアドレス
	 * @return 登録済であればtrue、未登録であればfalse
	 */
	public boolean isExistOtherUserByEmail(EditUserForm editUserForm) {
		User user = userRepository.findByEmail(editUserForm.getEmail());
		if(Objects.isNull(user)) {
			return false;
		}
		if(user.getUserId().equals(editUserForm.getUserId())) {
			return false;
		}
		return true;
	}
}