package com.honcari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

@Service
@Transactional
public class ShowGroupManagementService {
	
	@Autowired
	private GroupRepository groupRepository;
	
	public List<Group> showGroupListByBelongUserId(Integer userId){
		return groupRepository.findByBelongUserId(userId);
	}
	
	public List<Group> showGroupListByUserId(Integer userId){
		return groupRepository.findByUserId(userId);
	}

}
