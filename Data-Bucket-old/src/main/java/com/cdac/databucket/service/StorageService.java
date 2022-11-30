package com.cdac.databucket.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void uploadFile(MultipartFile file, String fileName);
	
	byte[] downloadFile(String fileName);
    void deleteFile(String fileName);

		
}
