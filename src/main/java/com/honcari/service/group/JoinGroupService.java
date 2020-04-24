package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.repository.GroupRelationRepository;

@Service
@Transactional
public class JoinGroupService {
	@Autowired
	private GroupRelationRepository grRepository;
	
	public void joinGroup(Integer userId,Integer groupId) {
		grRepository.insert(userId, groupId);
	}

}
