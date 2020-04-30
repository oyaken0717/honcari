package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.domain.User;
import com.honcari.repository.GroupRepository;
import com.honcari.repository.UserRepository;

@Service
@Transactional
public class ShowGroupManagementService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	public User showGroupListByBelongUserIdAndStatus(Integer userId,Integer status){
		return userRepository.findByUserIdAndRelationStatus(userId,status);
	}
	
	public List<Group> showGroupListByOwnerUserId(Integer userId){
		return groupRepository.findByOwnerUserId(userId);
	}

}