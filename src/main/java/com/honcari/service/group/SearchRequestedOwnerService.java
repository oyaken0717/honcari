package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

/**
 * オーナー権限委任依頼をするためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class SearchRequestedOwnerService {
	
	@Autowired
	private GroupRepository groupRepository;
	
	/**
	 * オーナー権限委任依頼をしたグループをユーザーIDから検索するメソッド
	 * 
	 * @param userId ユーザーID
	 * @return グループ情報のリスト
	 */
	public List<Group> searchRequestedOwnerUser(Integer userId) {
		return groupRepository.findByRequestedOwnerUserId(userId);
	}
	
	/**
	 * オーナー権限委任依頼をしたグループをカウントするメソッド
	 * 
	 * @param userId ユーザー
	 * @return グループ数
	 */
	public Integer countOwnerRequest(Integer userId) {
		return groupRepository.countOwnerRequest(userId);

	}

}
