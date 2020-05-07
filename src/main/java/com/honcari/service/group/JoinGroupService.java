package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

/**
 * グループへ参加するためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class JoinGroupService {
	@Autowired
	private GroupRelationRepository grRepository;

	/**
	 * グループへ参加するためのメソッド.
	 * 
	 * @param userId ユーザーid
	 * @param groupId グループid
	 */
	public void joinGroup(Integer userId, Integer groupId) {
		//ユーザー名とグループidでgroup_relation情報を取得
		GroupRelation gr = grRepository.findByUserIdAndGroupId(userId, groupId);

		//対象ユーザーとグループのgroup_relation情報が存在していない場合はインサート処理
		if (gr == null) {
			grRepository.insert(userId, groupId, 1);
			return;
		}
		
		//対象ユーザーとグループのgroup_relation情報が存在している場合はアップデート処理
		if (gr.getRelation_status() == 0 || gr.getRelation_status() == 9) {
			gr.setRelation_status(1);
			grRepository.update(gr);

		}

	}

}
