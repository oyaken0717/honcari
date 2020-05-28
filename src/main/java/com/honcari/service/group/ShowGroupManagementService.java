package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.domain.User;
import com.honcari.repository.GroupRepository;
import com.honcari.repository.UserRepository;

/**
 * グループ管理画面表示のために使うサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class ShowGroupManagementService {
	
	@Autowired
	private GroupRepository groupRepository;
	
	/**
	 * グループ関係情報のステータス参加しているグループを検査するためのメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param status グループ関係情報のステータス
	 * @return グループ情報リスト
	 */
	public List<Group> showGroupListByBelongUserIdAndRelationStatus(Integer userId,Integer status){
		return groupRepository.findByUserIdAndStatus(userId, status);
	}
	
	/**
	 * グループのステータスとグループ関係情報のステータスとユーザーIDでグループを検索するためのメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param groupStatus グループステータス
	 * @param relationStatus グループ関係情報のステータス
	 * @return グループ情報のリスト
	 */
	public List<Group> showGroupListByBelongUserIdAndGroupStatus(Integer userId,Integer groupStatus,Integer relationStatus){
		return groupRepository.findByUserIdAndGroupStatus(userId, groupStatus, relationStatus);
	}
	
	/**
	 * ユーザーが作成したグループを検索するためのメソッド.
	 * 
	 * @param userId ユーザーid
	 * @return グループ情報リスト
	 */
	public List<Group> showGroupListByOwnerUserId(Integer userId){
		return groupRepository.findByOwnerUserId(userId);
	}
	
	public List<Group> showGroupListByOwnerUserIdAndStatus(Integer userId,Integer status){
		return groupRepository.findByOwnerUserIdAndStatus(userId,status);
	}

}