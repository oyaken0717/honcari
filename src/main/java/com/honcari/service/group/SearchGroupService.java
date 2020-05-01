package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

@Service
@Transactional
public class SearchGroupService {
	
	@Autowired
	private GroupRepository groupRepository;
	
	public List<Group> searchGroup(String name) {
		
		return groupRepository.findByLikeName(name);
	}
	
	public Group searchGroupById(Integer id) {
		
		return groupRepository.findByGroupId(id);
	}
	
	

}
