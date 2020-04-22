package com.honcari.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.repository.GroupRelationshipRepository;

@Service
@Transactional
public class JoinGroupService {
	@Autowired
	private GroupRelationshipRepository grRepository;
	
	public void joinGroup(Integer userId,Integer groupId) {
		grRepository.insert(userId, groupId);
	}

}
