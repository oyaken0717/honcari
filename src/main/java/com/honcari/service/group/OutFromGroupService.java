package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

/**
 * グループを抜けるための処理を記載したサービス
 * 
 * @author yamaseki
 *
 */
@Transactional
@Service
public class OutFromGroupService {

	@Autowired
	private GroupRelationRepository groupRelationRepository;

	/**
	 * グループを抜けるメソッド.
	 * 
	 * @param userId ユーザーid
	 * @param groupId グループid
	 */
	public void outGroup(Integer userId, Integer groupId) {
		GroupRelation gr = groupRelationRepository.findByUserIdAndGroupId(userId, groupId);
		//group_relationのステータスを9（停止）に変更
		gr.setRelation_status(9);
		groupRelationRepository.update(gr);
	}

}
