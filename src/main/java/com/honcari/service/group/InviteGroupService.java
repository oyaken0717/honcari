package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.User;
import com.honcari.repository.GroupRelationRepository;

@Service
@Transactional
public class InviteGroupService {
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	

	
	public void inviteGroup(List<User> userList, Integer groupId) {
		userList.forEach(user -> {
			groupRelationRepository.insert(user.getUserId(),groupId,0);
		});
	}

}
