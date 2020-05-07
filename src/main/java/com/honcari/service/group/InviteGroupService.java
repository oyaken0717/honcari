package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.domain.User;
import com.honcari.repository.GroupRelationRepository;

/**
 * ユーザーをグループに招待するためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class InviteGroupService {

	@Autowired
	private GroupRelationRepository groupRelationRepository;

	/**
	 * ユーザーをグループに招待するためのメソッド.
	 * 
	 * @param userList ユーザー情報リスト
	 * @param groupId グループid
	 */
	public void inviteGroup(List<User> userList, Integer groupId) {

		userList.forEach(user -> {
			//ユーザー名とグループidでgroup_relation情報を取得
			GroupRelation gr = groupRelationRepository.findByUserIdAndGroupId(user.getUserId(), groupId);
			
			//対象ユーザーとグループのgroup_relation情報が存在していない場合はインサート処理
			if (gr == null) {
				groupRelationRepository.insert(user.getUserId(), groupId, 0);
				return;
			}
			
			//対象ユーザーとグループのgroup_relation情報が存在している場合はアップデート処理
			if (gr.getRelation_status() == 9) {
				gr.setRelation_status(0);
				groupRelationRepository.update(gr);
			}
		});
	}

}
