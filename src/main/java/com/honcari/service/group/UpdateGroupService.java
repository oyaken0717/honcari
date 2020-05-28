package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

/**
 * グループを更新するためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class UpdateGroupService {
	
	@Autowired
	private GroupRepository groupRepository;

	/**
	 * グループを更新するためのサービス
	 * 
	 * @param group グループ情報
	 */
	public void updateGroup(Group group) {
		groupRepository.update(group);
	}
}
