package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.repository.GroupRelationRepository;

/**
 * ユーザーに対するグループ招待承認待ちの数を検索するサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class CountInvitePendingService {
	
	@Autowired
	private GroupRelationRepository groupRelationRepository;
	
	/**
	 * グループ招待承認待ちの数を検索するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param status グループステータス
	 * @return ユーザーに対するグループ招待承認待ちの数
	 */
	public Integer countInvitePending(Integer userId,Integer status) {
		return groupRelationRepository.countByUserIdAndStatus(userId, status);
	}

}
