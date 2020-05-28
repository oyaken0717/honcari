package com.honcari.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

/**
 * グループ検索のためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class SearchGroupService {

	@Autowired
	private GroupRepository groupRepository;

	/**
	 * グループ名で検索するメソッド.
	 * 
	 * @param name グループ名
	 * @return グループ情報リスト
	 */
	public List<Group> searchGroup(String name,Integer offset) {
		return groupRepository.findByLikeName(name,offset);
	}

	/**
	 * グループidでグループ検索するメソッド.
	 * 
	 * @param id グループid
	 * @return グループ情報
	 */
	public Group searchGroupById(Integer id) {
		return groupRepository.findByGroupId(id);
	}
	
	/**
	 * グループの名前でグループ件数を検索するメソッド.
	 * 
	 * @param name グループ名
	 * @return
	 */
	public Integer count(String name) {
		return groupRepository.count(name);
	}

}
