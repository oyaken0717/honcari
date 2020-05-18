package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.repository.GroupRelationRepository;

@Service
@Transactional
public class CountInvitePendingService {
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	public Integer countInvitePending(Integer userId,Integer status) {
		return groupRelationRepository.countByUserIdAndStatus(userId, status);
	}

}
