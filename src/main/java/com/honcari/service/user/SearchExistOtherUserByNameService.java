package com.honcari.service.user;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.form.EditUserForm;
import com.honcari.repository.UserRepository;

/**
 * 名前が登録済か否かを確認するサービス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class SearchExistOtherUserByNameService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 名前が登録済か否かを確認するメソッド.
	 * 
	 * @param email メールアドレス
	 * @return 登録済であればtrue、未登録であればfalse
	 */
	public boolean isExistOtherUserByName(EditUserForm editUserForm) {
		User user = userRepository.findAnyUserByName(editUserForm.getName());
		if(Objects.isNull(user)) {
			return false;
		}
		if(user.getUserId().equals(editUserForm.getUserId())) {
			return false;
		}
		return true;
	}
}