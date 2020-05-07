package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honcari.domain.Group;
import com.honcari.repository.GroupRepository;

/**
 * グループ詳細情報を検索するためのサービス
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class ShowGroupDetailService {

	@Autowired
	private GroupRepository groupRepository;

	/**
	 * グループidからグループ詳細情報を検索するためのメソッド.
	 * 
	 * @param groupId グループid
	 * @return グループ情報
	 */
	@RequestMapping("/show_group_detail")
	public Group showGroupDetail(Integer groupId) {
		return groupRepository.findByGroupIdAndRelationStatus(groupId);
	}

}
