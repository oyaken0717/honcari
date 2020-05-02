package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

@Transactional
@Service
public class OutFromGroupService {
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	public void outGroup(Integer userId,Integer groupId) {
		GroupRelation gr = groupRelationRepository.findByUserIdAndGroupId(userId, groupId);
		gr.setRelation_status(9);
		groupRelationRepository.update(gr);
	}
	
}
