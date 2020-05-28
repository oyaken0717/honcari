package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.GroupRelation;
import com.honcari.repository.GroupRelationRepository;

/**
 * グループ関係情報を取得するためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class SearchGroupRelationService {
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	/**
	 * グループ関係情報を取得するためのサービス.
	 * 
	 * @param userId ユーザーID
	 * @param groupId グループID
	 * @param status グループ関係ステータス
	 * @return グループ関係情報のリスト
	 */
	public GroupRelation searchByUserIdAndGroupIdAndStatus(Integer userId,Integer groupId,Integer status) {
		return groupRelationRepository.findByUserIdAndGroupIdAndStatus(userId, groupId,status);
	}
}
