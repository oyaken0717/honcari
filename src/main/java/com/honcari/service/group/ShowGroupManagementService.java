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
	private UserRepository userRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	/**
	 * ユーザーが所属しているグループを検索するためのメソッド.
	 * 
	 * @param userId ユーザーid
	 * @param status ステータス
	 * @return ユーザー情報
	 */
//	public User showGroupListByBelongUserIdAndStatus(Integer userId,Integer status){
//		return userRepository.findByUserIdAndRelationStatus(userId,status);
//	}
	public List<Group> showGroupListByBelongUserIdAndStatus(Integer userId,Integer status){
		return groupRepository.findByUserIdAndStatus(userId, status);
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

}