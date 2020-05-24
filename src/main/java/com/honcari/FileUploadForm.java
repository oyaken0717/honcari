package com.honcari;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {
	
	private MultipartFile uploadFile;

	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}

}
