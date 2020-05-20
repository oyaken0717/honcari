package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.domain.User;
import com.honcari.form.EditGroupForm;
import com.honcari.repository.GroupRepository;

/**
 * グループ情報を編集するためのサービス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class EditGroupService {
	
	@Autowired
	private GroupRepository groupRepositroty;
	
	@Autowired
	private RegisterGroupService registerGroupService;
	
	/**
	 * グループ情報編集のためのメソッド.
	 * 
	 * @param form グループ編集フォーム
	 */
	public void editGroup(EditGroupForm form) {

		Group group = new Group();
		group.setId(form.getGroupId());
		group.setName(form.getName());
		group.setDescription(form.getDescription());
		group.setGroupStatus(form.getGroupStatus());
		if(form.getGroupStatus()==null) {
			group.setGroupStatus(0);	
		}
		group.setOwnerUserId(form.getOwnerUserId());
		group.setRequestedOwnerUserId(null);
		//もしオーナー権限委任をする場合、requestedOwnwerUserIdにユーザーIDを詰める
		if(form.getUserName()!=null) {
			User user = registerGroupService.findByName(form.getUserName());
			group.setRequestedOwnerUserId(user.getUserId());
		}
		groupRepositroty.update(group);
	}

}
