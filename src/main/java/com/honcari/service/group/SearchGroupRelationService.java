package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

@Service
@Transactional
public class SearchGroupRelationService {
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	public GroupRelation searchByUserIdAndGroupIdAndStatus(Integer userId,Integer groupId,Integer status) {
		return groupRelationRepository.findByUserIdAndGroupIdAndStatus(userId, groupId,status);
	}
}
