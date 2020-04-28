package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.domain.Group;
import com.honcari.form.EditGroupForm;
import com.honcari.repository.GroupRepository;

@Service
@Transactional
public class EditGroupService {
	
	@Autowired
	private GroupRepository groupRepositroty;
	
	public void editGroup(EditGroupForm form) {
		Group group = new Group();
		group.setId(form.getGroupId());
		group.setName(form.getName());
		group.setDescription(form.getDescription());
		group.setGroupStatus(form.getGroupStatus());
		group.setOwnerUserId(form.getOwnerUserId());
		groupRepositroty.update(group);
	}

}
