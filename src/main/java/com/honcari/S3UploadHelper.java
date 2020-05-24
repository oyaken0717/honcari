package com.honcari;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.util.IOUtils;

@Component
public class S3UploadHelper {
	
    private static final String S3_BUCKET_PREFIX = "s3://";
    private static final String DIRECTORY_DELIMITER = "/";
    private String Group_Folder_Name = System.getenv("AWS_GROUP_FOLDER_NAME");
    private String User_Folder_Name = System.getenv("AWS_USER_FOLDER_NAME");
	private  String Bucket_Name = System.getenv("AWS_BUCKET_NAME");

    @Autowired
    private ResourceLoader resourceLoader;

//    @Autowired
//    AmazonS3 amazonS3;

    public String saveGroupFile(MultipartFile multipartFile,Integer groupId,HttpServletRequest request){
		if (!request.getHeader("REFERER").contains("heroku")) {
			Group_Folder_Name="group-test";
			Bucket_Name = "honcari-image-test";
		}
    	String objectKey = new StringBuilder()
          .append(S3_BUCKET_PREFIX)
          .append(Bucket_Name)
          .append(DIRECTORY_DELIMITER)
          .append(Group_Folder_Name)
          .append(DIRECTORY_DELIMITER)
          .append(groupId)
          .toString();
        WritableResource writableResource = (WritableResource)resourceLoader.getResource(objectKey);        
        try(InputStream inputStream = multipartFile.getInputStream();
                OutputStream outputStream = writableResource.getOutputStream()){
        	IOUtils.copy(inputStream, outputStream);
            
        }catch (IOException e){
            e.printStackTrace();
        }
        return objectKey;
     }
    
    public String saveUserFile(MultipartFile multipartFile,Integer userId,HttpServletRequest request){
		if (!request.getHeader("REFERER").contains("heroku")) {
			User_Folder_Name="profile-image-test";
			Bucket_Name = "honcari-image-test";
		}
    	String objectKey = new StringBuilder()
          .append(S3_BUCKET_PREFIX)
          .append(Bucket_Name)
          .append(DIRECTORY_DELIMITER)
          .append(User_Folder_Name)
          .append(DIRECTORY_DELIMITER)
          .append(userId)
          .toString();
        WritableResource writableResource = (WritableResource)resourceLoader.getResource(objectKey);        
        try(InputStream inputStream = multipartFile.getInputStream();
                OutputStream outputStream = writableResource.getOutputStream()){
        	IOUtils.copy(inputStream, outputStream);
            
        }catch (IOException e){
            e.printStackTrace();
        }
        return objectKey;
     }


}
