package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationshipRepository;

@Service
@Transactional
public class SearchUserInGroupService {
	
	@Autowired
	private GroupRelationshipRepository groupRelationshipRepository;
	
	public GroupRelation searchUser(Integer userId,Integer groupId) {
		return groupRelationshipRepository.findByUserIdAndGroupId(userId,groupId);
	}

}
