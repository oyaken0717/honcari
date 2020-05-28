package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

/**
 * グループ招待キャンセルのためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class CancelInviteService {
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	/**
	 * グループ招待キャンセルのためのメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param groupId グループID
	 */
	public void cancelInvite(Integer userId, Integer groupId) {
		GroupRelation gr = groupRelationRepository.findByUserIdAndGroupId(userId, groupId);
		gr.setRelation_status(9);
		gr.setSendInviteUserId(0);
		groupRelationRepository.update(gr);
	}
	
	

}
