package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.domain.User;
import com.honcari.repository.GroupRelationRepository;
import com.honcari.repository.GroupRepository;
import com.honcari.repository.UserRepository;

/**
 * グループ作成のためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class RegisterGroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private GroupRelationRepository groupRelationRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * グループ作成のためのメソッド.
	 * 
	 * @param group グループ情報
	 * @param userList ユーザー情報リスト
	 * @return グループ情報
	 */
	public Group insertGroup(Group group, List<User> userList) {
				
		//グループを登録、戻り値にidを取得.
		Group groupInId = groupRepository.insertGroup(group);
		//オーナーユーザのみ、relation_statusを1にして登録
		groupRelationRepository.insert(group.getOwnerUserId(),groupInId.getId(),1);
		//グループに入れるuser数だけ登録.
		if(!userList.isEmpty()) {	
		userList.forEach(user -> {
			groupRelationRepository.insert(user.getUserId(), groupInId.getId(),0);
		});
	  }
		return groupInId;
	}

	/**
	 * ユーザー名で検索するためのメソッド.
	 * 
	 * @param name ユーザー名
	 * @return ユーザー情報
	 */
	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	/**
	 * ユーザー名で曖昧検索するためのメソッド.
	 * 
	 * @param name ユーザー名
	 * @param userId ユーザーid
	 * @return ユーザー情報リスト
	 */
	public List<User> findByNameLike(String name, Integer userId) {
		return userRepository.findByNameLike(name, userId);
	}
	
	/**
	 * グループidとユーザー名で曖昧検索するためのメソッド.
	 * 
	 * @param name ユーザー名
	 * @param userId ユーザーid
	 * @param groupId グループid
	 * @return ユーザー情報リスト
	 */
	public List<User> findByNameLikeAndGroupId(String name, Integer userId, Integer groupId) {
		return userRepository.findByNameLikeAndGroupId(name, userId,groupId);
	}
}
