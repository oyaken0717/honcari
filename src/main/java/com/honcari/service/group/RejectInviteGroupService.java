package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

@Service
@Transactional
public class RejectInviteGroupService {
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	public void rejectInviteGroup(Integer userId,Integer groupId) {
		GroupRelation gr = groupRelationRepository.findByUserIdAndGroupId(userId, groupId);
		System.out.println(userId);
		gr.setRelation_status(9);
		groupRelationRepository.update(gr);
	}

}
