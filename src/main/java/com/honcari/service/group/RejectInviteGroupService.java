package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

/**
 * グループへの招待を拒否するためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class RejectInviteGroupService {

	@Autowired
	private GroupRelationRepository groupRelationRepository;

	/**
	 * グループへの招待を拒否するためのメソッド.
	 * 
	 * @param userId ユーザーid
	 * @param groupId グループid
	 */
	public void rejectInviteGroup(Integer userId, Integer groupId) {
		GroupRelation gr = groupRelationRepository.findByUserIdAndGroupId(userId, groupId);
		gr.setRelation_status(9);
		groupRelationRepository.update(gr);
	}

}
