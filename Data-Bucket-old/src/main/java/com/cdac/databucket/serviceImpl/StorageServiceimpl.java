package com.cdac.databucket.serviceImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.cdac.databucket.service.StorageService;

@Service

public class StorageServiceimpl implements StorageService{

	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${application.bucket.name}")
	private String bucketName;
	
	
	
	@Override
	public void uploadFile(MultipartFile file, String fileName) {
		// TODO Auto-generated method stub
		File fileObj  = convertMultiPartFileToFile(file);
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
		fileObj.delete();
		
	}

	
	
	private File convertMultiPartFileToFile(MultipartFile file) {
		
		 File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
	        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
	            fos.write(file.getBytes());
	        } catch (IOException e) {
	            System.out.println("Error converting multipartFile to fileT");
	        }
	        return convertedFile;
		
		
	}



	@Override
	public byte[] downloadFile(String fileName) {
		// TODO Auto-generated method stub
		S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}



	@Override
	public void deleteFile(String fileName) {
		// TODO Auto-generated method stub
		s3Client.deleteObject(bucketName, fileName);
	}
	
	
	
	
	
	
	
	
}
