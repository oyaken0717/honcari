package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

@Service
@Transactional
public class ShowGroupDetailService {
	
	@Autowired
	private GroupRepository groupRepository;
	
	@RequestMapping("/show_group_detail")
	public Group showGroupDetail(Integer groupId) {
		return groupRepository.findByGroupIdAndRelationStatus(groupId);
	}


}
