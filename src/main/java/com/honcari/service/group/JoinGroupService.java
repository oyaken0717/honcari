package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

@Service
@Transactional
public class JoinGroupService {
	@Autowired
	private GroupRelationRepository grRepository;
	
	public void joinGroup(Integer userId,Integer groupId) {
		GroupRelation gr = grRepository.findByUserIdAndGroupIdAndStatus(userId, groupId, 0);
		if(gr != null) {
			gr.setRelation_status(1);
			grRepository.update(gr);

		}else {
			grRepository.insert(userId, groupId,1);
		}
	}

}
