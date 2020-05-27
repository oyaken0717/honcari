package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

@Service
@Transactional
public class SeachInvitedUserInGroupService {

	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	public List<GroupRelation> seachInvitedUser(Integer groupId,Integer sendInviteUserId) {
		return groupRelationRepository.findByGroupIdAndStatusAndSendInviteUserId(groupId, 0, sendInviteUserId);
	}
}
