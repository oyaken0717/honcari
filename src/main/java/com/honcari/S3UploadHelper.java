package com.honcari;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private static final String GROUP_FOLDER_NAME = System.getenv("AWS_GROUP_FOLDER_NAME");
    private static final String USER_FOLDER_NAME = System.getenv("AWS_USER_FOLDER_NAME");


//    @Value("")
	private static final String BUCKET_NAME = System.getenv("AWS_BUCKET_NAME");

    @Autowired
    private ResourceLoader resourceLoader;

//    @Autowired
//    AmazonS3 amazonS3;

    public String saveGroupFile(MultipartFile multipartFile,Integer groupId){
    	String objectKey = new StringBuilder()
          .append(S3_BUCKET_PREFIX)
          .append(BUCKET_NAME)
          .append(DIRECTORY_DELIMITER)
          .append(GROUP_FOLDER_NAME)
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
    
    public String saveUserFile(MultipartFile multipartFile,Integer userId){
    	String objectKey = new StringBuilder()
          .append(S3_BUCKET_PREFIX)
          .append(BUCKET_NAME)
          .append(DIRECTORY_DELIMITER)
          .append(USER_FOLDER_NAME)
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
