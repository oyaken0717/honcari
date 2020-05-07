package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

/**
 * group_relation情報を検索するためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class SearchUserInGroupService {
	
	@Autowired
	private GroupRelationRepository groupRelationshipRepository;
	
	/**
	 * グループidとユーザー名でgroup_relation情報を検索するためのメソッド.
	 * 
	 * @param userId ユーザーid
	 * @param groupId グループid
	 * @return group_relaltion情報
	 */
	public GroupRelation searchUser(Integer userId,Integer groupId) {
		return groupRelationshipRepository.findByUserIdAndGroupId(userId,groupId);
	}

}
