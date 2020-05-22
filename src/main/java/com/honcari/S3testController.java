package com.honcari;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class S3testController {
	
//	@Autowired
//    S3UploadHelper s3UploadHelper;
//	
//	@RequestMapping("/to-test")
//	public String toTest() {
//		return "s3_test";
//	}
//	
//	@PostMapping("upload")
//    public String upload(FileUploadForm fileUploadModel){
//        s3UploadHelper.saveFile(fileUploadModel.getUploadFile());
//        return "redirect:/to-test";
//    }
	
//	@PostMapping("/upload-test")
//	public String s3Test(@RequestParam("file") MultipartFile multipartFile) {
//		System.out.println(multipartFile.getContentType());
//		
//		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
//		
//		try {
//            // ファイルをS3にアップロードする
//            s3.putObject("バケット名", "オブジェクト名", new File("ファイルパス"));
//        } catch (AmazonServiceException e) {
//            System.err.println(e.getErrorMessage());
//            System.exit(1);
//        }
//        System.out.println("Done!");
//		
//		return "s3_test";
//	}

}
