package com.cdac.databucket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdac.databucket.service.SharingService;


@Controller
@RequestMapping("/Remove")
public class RemoveSharingControlle {

	 @Autowired
	 private SharingService sharingService;

	    @PostMapping("/FolderPermission")
	    public String removeSharedFolder(@RequestParam("sharedFolderId") long folderId) {
	        sharingService.removeFolderSharing(folderId);
	        return "redirect:/SharedByMe";
	    }

	    @PostMapping("/FilePermission")
	    public String removeSharedFile(@RequestParam("sharedFileId") long fileId) {
	        sharingService.removeFileSharing(fileId);
	        return "redirect:/SharedByMe";
	    }
	
}
