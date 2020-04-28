package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.repository.GroupRelationRepository;

@Transactional
@Service
public class DeleteGroupRelationService {
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	public void deleteGroup(Integer userId,Integer groupId) {
		groupRelationRepository.deleteByUserIdAndGroupId(userId, groupId);
	}
	
	public void deleteGroupByGroupId(Integer groupId) {
		groupRelationRepository.deleteByGroupId(groupId);
	}

}
