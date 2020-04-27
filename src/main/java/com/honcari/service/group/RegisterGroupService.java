package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.domain.User;
import com.honcari.repository.GroupRelationRepository;
import com.honcari.repository.GroupRepository;
import com.honcari.repository.UserRepository;

@Service
@Transactional
public class RegisterGroupService {

	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void insertGroup(Group group, List<User> userList) {
		//グループを登録、戻り値にidを取得.
		Group groupInId = groupRepository.insertGroup(group);
		//グループに入れるuser数だけ登録.
		userList.forEach(user -> {
			groupRelationRepository.insert(user.getUserId(), groupInId.getId());
		});
	}
	
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
	
	public List<User> findByNameLike(String name, Integer userId) {
		return userRepository.findByNameLike(name, userId);
	}
}
