package com.cdac.databucket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.cdac.databucket.entity.Folder;



public interface FolderService {
	 List<Folder> findFoldersInsideFolder(Optional<Integer> folderId, String username, Optional<String> search);

	    Optional<Folder> findById(Long folderId);

	    void createFolder(String folderName, Optional<Long> folderId, String username);

	    void deleteFolder(Long deleteFolderId, String username);

	    Long getPrevFolderId(Optional<Long> folderId, String name);

	    ResponseEntity<StreamingResponseBody> downloadFolder(Long folderId);

	    String shareViaEmail(Long folderId);

	    ResponseEntity<StreamingResponseBody> downloadSharedFolder(Long id);
}
