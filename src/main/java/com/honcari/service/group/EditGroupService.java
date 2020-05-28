package com.honcari.service.group;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honcari.common.S3UploadHelper;
import com.honcari.domain.Group;
import com.honcari.domain.User;
import com.honcari.form.EditGroupForm;

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
	
	private String Bucket_Name = System.getenv("AWS_BUCKET_NAME");    
	private String Group_Folder_Name = System.getenv("AWS_GROUP_FOLDER_NAME");
	
	/**
	 * グループ情報編集のためのメソッド.
	 * 
	 * @param form グループ編集フォーム
	 */
	public Group editGroup(EditGroupForm form,HttpServletRequest request) {
		Group group = searchGroupService.searchGroupById(form.getGroupId());
		group.setName(form.getName());
		group.setDescription(form.getDescription());
		group.setGroupStatus(form.getGroupStatus());
		if(form.getGroupStatus()==null) {
			group.setGroupStatus(0);	
		}
		
		//もしオーナー権限委任をする場合、requestedOwnwerUserIdにユーザーIDを詰める
		if(form.getUserName()!=null) {
			User user = registerGroupService.findByName(form.getUserName());
			group.setRequestedOwnerUserId(user.getUserId());
		}
		if(!"".equals(form.getGroupImage().getOriginalFilename())) {
			s3UploadHelper.saveGroupFile(form.getGroupImage(),group.getId(),request);
			if (!request.getHeader("REFERER").contains("heroku")) {
				Group_Folder_Name = "group-test";
				Bucket_Name = "honcari-image-test";
			}
			String groupImageUrl = "https://"+Bucket_Name+".s3-ap-northeast-1.amazonaws.com/"+Group_Folder_Name+"/"+group.getId();
	    	group.setGroupImage(groupImageUrl);
		}else {
			group.setGroupImage(form.getImagePath());
		}
    	 updateGroupService.updateGroup(group);
		return group;
	}

}
