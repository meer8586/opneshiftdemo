package com.cdac.databucket.service;

import java.util.List;

import com.cdac.databucket.entity.File;
import com.cdac.databucket.entity.Folder;


public interface SharingService {
	 void shareFile(long fileId, String userEmail);

	    void shareFolder(long folderId, String userEmail);

	    List<File> getAllShareFilesByUser(long id);

	    List<Folder> getAllShareFoldersByUser(long id);

	    String generateUrl(long id);

	    void removeFolderSharing(long id);

	    void removeFileSharing(long id);
}
