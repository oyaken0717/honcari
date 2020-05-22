package com.honcari.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.S3UploadHelper;
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
	private RegisterGroupService registerGroupService;
	
	@Autowired
	private UpdateGroupService updateGroupService;
	
	@Autowired
    private S3UploadHelper s3UploadHelper;
	
	@Autowired
	private SearchGroupService searchGroupService;
	
	private static final String BUCKET_NAME = System.getenv("AWS_BUCKET_NAME");    
	private static final String GROUP_FOLDER_NAME = System.getenv("AWS_GROUP_FOLDER_NAME");
	
	/**
	 * グループ情報編集のためのメソッド.
	 * 
	 * @param form グループ編集フォーム
	 */
	public Group editGroup(EditGroupForm form) {
		Group group = searchGroupService.searchGroupById(form.getGroupId());
		group.setName(form.getName());
		group.setDescription(form.getDescription());
		group.setGroupStatus(form.getGroupStatus());
		if(form.getGroupStatus()==null) {
			group.setGroupStatus(0);	
		}
//		group.setOwnerUserId(form.getOwnerUserId());
//		group.setRequestedOwnerUserId(null);
		
		//もしオーナー権限委任をする場合、requestedOwnwerUserIdにユーザーIDを詰める
		if(form.getUserName()!=null) {
			User user = registerGroupService.findByName(form.getUserName());
			group.setRequestedOwnerUserId(user.getUserId());
		}
		if(!"".equals(form.getGroupImage().getOriginalFilename())) {
			s3UploadHelper.saveGroupFile(form.getGroupImage(),group.getId());
			String groupImageUrl = "https://"+BUCKET_NAME+".s3-ap-northeast-1.amazonaws.com/"+GROUP_FOLDER_NAME+"/"+group.getId();
	    	group.setGroupImage(groupImageUrl);
		}
    	 updateGroupService.updateGroup(group);
		return group;
	}

}
