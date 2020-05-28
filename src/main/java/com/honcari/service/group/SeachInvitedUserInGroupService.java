package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

/**
 * グループ招待を送ったユーザーを元にグループ関係情報を取得するサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class SeachInvitedUserInGroupService {

	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	/**
	 * グループ招待を送ったユーザーを元にグループ関係情報を取得するメソッド.
	 * 
	 * @param groupId グループID
	 * @param sendInviteUserId グループ招待を送ったユーザーのユーザーID
	 * @return グループ関係情報のリスト
	 */
	public List<GroupRelation> seachInvitedUser(Integer groupId,Integer sendInviteUserId) {
		return groupRelationRepository.findByGroupIdAndStatusAndSendInviteUserId(groupId, 0, sendInviteUserId);
	}
}
