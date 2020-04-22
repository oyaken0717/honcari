package com.honcari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRealationship;
import com.honcari.repository.GroupRelationshipRepository;

@Service
@Transactional
public class SearchUserInGroupService {
	
	@Autowired
	private GroupRelationshipRepository groupRelationshipRepository;
	
	public GroupRealationship searchUser(Integer userId,Integer groupId) {
		return groupRelationshipRepository.findByUserIdAndGroupId(userId,groupId);
	}

}
