package com.cdac.databucket.serviceImpl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.databucket.config.StorageConfig;
import com.cdac.databucket.entity.File;
import com.cdac.databucket.entity.Folder;
import com.cdac.databucket.repository.FileRepository;
import com.cdac.databucket.repository.FolderRepository;
import com.cdac.databucket.repository.UserRepository;
import com.cdac.databucket.service.FileService;
import com.cdac.databucket.service.StorageService;
import com.cdac.databucket.specification.FileSpecification;
import com.cdac.databucket.entity.User;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileRepository fileRepository;
	    
	@Autowired
	private UserRepository userRepository;
	    
	@Autowired
	private  FolderRepository folderRepository;
	
	@Autowired
    private StorageService storageService;

	@Autowired
	private FileSpecification fileSpecification;
	
	@Override
	public void saveFile(Optional<Long> folderId, MultipartFile file, String username) {
		// TODO Auto-generated method stub
		File newFile = new File();
		
		Optional<User> user = userRepository.findByEmail(username);
		user.ifPresent(newFile::setUser);
		folderId.ifPresent(aLong -> {
			
			Optional<Folder> folder = folderRepository.findById(aLong);
			folder.ifPresent(newFile::setFolder);
			
		});
		
		newFile.setFileName(file.getOriginalFilename());
		fileRepository.save(newFile);
		storageService.uploadFile(file, newFile.getId().toString());
		
		
	}


	@Override
	public byte[] downloadFile(Long fileId) {
		// TODO Auto-generated method stub
		return storageService.downloadFile(fileId.toString());
	}


	@Override
	public Optional<File> findById(Long fileId) {
		// TODO Auto-generated method stub
		return fileRepository.findById(fileId);
	}


	@Override
	public List<File> findFilesInsideFolders(Optional<Integer> folderId,  String username,
			Optional<String> search) {
		 if (search.isPresent()) {
	            return fileRepository.findAll(fileSpecification.withUser(username).and(fileSpecification.search(search.orElse(null))));
	        } else {
	            return fileRepository.findAll(fileSpecification.insideFolder(folderId.orElse(null)).and(fileSpecification.withUser(username)));
	        }
	}


	@Override
	public void deleteFile(Long fileId, String username) {
		// TODO Auto-generated method stub
		 Optional<User> user = userRepository.findByEmail(username);
	        if (user.isPresent()) {
	            fileRepository.deleteById(fileId);
	            storageService.deleteFile(fileId.toString());
	        }
	}

}
