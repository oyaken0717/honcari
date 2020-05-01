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

	public void joinGroup(Integer userId, Integer groupId) {
		GroupRelation gr = grRepository.findByUserIdAndGroupId(userId, groupId);

		if (gr == null) {
			grRepository.insert(userId, groupId, 1);
			return;
		}

		if (gr.getRelation_status() == 0 || gr.getRelation_status() == 9) {
			gr.setRelation_status(1);
			grRepository.update(gr);

		}

	}

}
