package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

/**
 * ユーザidに一致したグループを取得するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class SearchGroupByUserIdService {

	@Autowired
	private GroupRepository groupRepository;
	
	/**
	 * ユーザidに一致したグループを取得するメソッド.
	 * 
	 * @param userId ユーザid
	 */
	public List<Group> searchGroupByUserId(Integer userId) {
		return groupRepository.findByUserId(userId);
	}
}
