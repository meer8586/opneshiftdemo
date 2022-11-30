package com.cdac.databucket.serviceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.cdac.databucket.entity.File;
import com.cdac.databucket.entity.Folder;
import com.cdac.databucket.entity.SharedFile;
import com.cdac.databucket.entity.SharedFolder;
import com.cdac.databucket.entity.User;
import com.cdac.databucket.repository.FileRepository;
import com.cdac.databucket.repository.FolderRepository;
import com.cdac.databucket.repository.SharedFileRepository;
import com.cdac.databucket.repository.SharedFolderRepository;
import com.cdac.databucket.repository.UserRepository;
import com.cdac.databucket.service.SharingService;


@Service
public class SharingServiceImpl implements SharingService {

	 @Autowired
	    private SharedFileRepository sharedFileRepository;

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private FileRepository fileRepository;

	    @Autowired
	    private FolderRepository folderRepository;

	    @Value("${application.bucket.name}")
	    String bucketName;

	    @Value("${cloud.aws.region.static}")
	    Regions region;

	    @Autowired
	    private AmazonS3 s3Client;

	    @Autowired
	    private SharedFolderRepository sharedFolderRepository;
	
	
	@Override
	public void shareFile(long fileId, String userEmail) {
		 System.out.println("File Id: " + fileId + "/nEmail: " + userEmail);
	        Optional<User> user = userRepository.findByEmail(userEmail);
	        File file = fileRepository.getOne(fileId);
	        boolean isAlreadyExist = false;
	        List<SharedFolder> sharedFileList = sharedFolderRepository.findAll();
	        List<Folder> parentFolders = new ArrayList<>();
	        Folder temp = file.getFolder();
	        while (temp != null) {
	            parentFolders.add(temp);
	            temp = temp.getParent();
	        }
	        for (SharedFolder sharedFolder : sharedFileList) {
	            if (parentFolders.contains(sharedFolder.getFolder()) && sharedFolder.getUser().getEmail().equals(userEmail)) {
	                isAlreadyExist = true;
	            }
	        }
	        List<File> files = new ArrayList<>();
	        List<SharedFile> sharedFiles = sharedFileRepository.findAll();
	        for (SharedFile sharedFile : sharedFiles) {
	            if (sharedFile.getUser().getEmail().equals(userEmail))
	                files.add(sharedFile.getFile());
	        }
	        if (files.contains(file)) {
	            isAlreadyExist = true;
	        }
	        if (user.isPresent()) {
	            if (!isAlreadyExist) {
	                SharedFile sharedFile = new SharedFile();
	                sharedFile.setUser(user.get());
	                sharedFile.setFile(file);
	                sharedFileRepository.save(sharedFile);
	            }
	        } else {
	            System.out.println(userEmail + "\n");
	            System.out.println("User not found\n");
	        }
		
	}

	@Override
	public void shareFolder(long folderId, String userEmail) {
		 Optional<User> user = userRepository.findByEmail(userEmail);
	        Folder folder = folderRepository.getOne(folderId);
	        List<SharedFolder> allSharedFolders = sharedFolderRepository.findAll();
	        List<Folder> parentFolders = new ArrayList<>();
	        Folder temp = folder;
	        while (temp != null) {
	            parentFolders.add(temp);
	            temp = temp.getParent();
	        }
	        boolean isAlreadyExist = false;
	        for (SharedFolder sharedFolder : allSharedFolders) {
	            if (parentFolders.contains(sharedFolder.getFolder()) && sharedFolder.getUser().getEmail().equals(userEmail)) {
	                isAlreadyExist = true;
	            }
	        }
	        if (user.isPresent()) {
	            if (!isAlreadyExist) {
	                SharedFolder sharedFolder = new SharedFolder();
	                sharedFolder.setUser(user.get());
	                sharedFolder.setFolder(folder);
	                sharedFolderRepository.save(sharedFolder);
	            }
	        } else {
	            System.out.println(userEmail + "\n");
	            System.out.println("User not found\n");
	        }
		
	}

	@Override
	public List<File> getAllShareFilesByUser(long id) {
		 List<SharedFile> sharedFiles = sharedFileRepository.getAllByUser(id);
	        List<File> files = new ArrayList<>();
	        for (SharedFile sharedFile : sharedFiles) {
	            if (sharedFile.getFile() != null)
	                files.add(sharedFile.getFile());
	        }
	        return files;
	}

	@Override
	public List<Folder> getAllShareFoldersByUser(long id) {
		List<SharedFolder> sharedFolders = sharedFolderRepository.getAllByUser(id);
        List<Folder> folders = new ArrayList<>();
        for (SharedFolder sharedFolder : sharedFolders) {
            if (sharedFolder.getFolder() != null)
                folders.add(sharedFolder.getFolder());
        }
        return folders;
	}

	@Override
	public String generateUrl(long id) {
		 String objectKey = Long.toString(id);

	        System.out.println(objectKey + "\n" + bucketName + "\n" + region);
	        try {

	            java.util.Date expiration = new java.util.Date();
	            long expTimeMillis = expiration.getTime();
	            expTimeMillis += 1000 * 60 * 60 * 4;
	            expiration.setTime(expTimeMillis);

	            GeneratePresignedUrlRequest generatePresignedUrlRequest =
	                    new GeneratePresignedUrlRequest(bucketName, objectKey)
	                            .withMethod(HttpMethod.GET).withExpiration(expiration);
	            if (generatePresignedUrlRequest != null) {
	                System.out.println("NULL\n");
	            }
	            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
	            return url.toString();
	        } catch (AmazonServiceException e) {
	            e.printStackTrace();
	        } catch (SdkClientException e) {
	            e.printStackTrace();
	        }
	        return "";
	}

	@Override
	public void removeFolderSharing(long id) {
		sharedFolderRepository.deleteById(id);
		
	}

	@Override
	public void removeFileSharing(long id) {
		 sharedFileRepository.deleteById(id);
		
	}

	
	
	
}
