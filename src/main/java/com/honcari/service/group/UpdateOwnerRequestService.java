package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

@Service
@Transactional
public class UpdateOwnerRequestService {
	
	@Autowired
	private GroupRepository groupRepository;
	
	public void updateOwnerRequest(Group group) {
		groupRepository.update(group);
	}

}
