package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.domain.User;
import com.honcari.repository.GroupRelationRepository;

@Service
@Transactional
public class InviteGroupService {

	@Autowired
	private GroupRelationRepository groupRelationRepository;

	public void inviteGroup(List<User> userList, Integer groupId) {

		userList.forEach(user -> {
			GroupRelation gr = groupRelationRepository.findByUserIdAndGroupId(user.getUserId(), groupId);

			if (gr == null) {
				groupRelationRepository.insert(user.getUserId(), groupId, 0);
				return;
			}

			if (gr.getRelation_status() == 9) {
				gr.setRelation_status(0);
				groupRelationRepository.update(gr);
			}
		});
	}

}
