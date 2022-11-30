package com.cdac.databucket.service;


import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.cdac.databucket.entity.File;

public interface FileService {

	 List<File> findFilesInsideFolders(Optional<Integer> folderId, String username, Optional<String> search);
	void saveFile(Optional<Long> folderId, MultipartFile file, String username);
	
	void deleteFile(Long fileId, String username);
	
	byte[] downloadFile(Long fileId);
	
	Optional<File> findById(Long fileId);
	
	
}
