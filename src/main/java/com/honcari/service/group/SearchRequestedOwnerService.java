package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

@Service
@Transactional
public class SearchRequestedOwnerService {
	
	@Autowired
	private GroupRepository groupRepository;
	
	public List<Group> searchRequestedOwnerUser(Integer userId) {
		return groupRepository.findByRequestedOwnerUserId(userId);
	}
	
	public Integer countOwnerRequest(Integer userId) {
		return groupRepository.countOwnerRequest(userId);

	}

}
