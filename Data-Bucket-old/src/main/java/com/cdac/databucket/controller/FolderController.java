package com.cdac.databucket.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


import com.cdac.databucket.service.FolderService;




@Controller
public class FolderController {

	 
	 
	 @Autowired
	  private FolderService folderService;
	 
	
	
	 @PostMapping("/create")
	    public String createFolder(Principal principal,
	                               @RequestParam(value = "folderId") Optional<Long> folderId,
	                               @RequestParam(value = "folderName") String folderName) {
	        folderService.createFolder(folderName, folderId, principal.getName());
	        return folderId.map(aLong -> "redirect:/home?folderId=" + aLong).orElse("redirect:/home");
	    }

	    @PostMapping("/deleteFolder")
	    public String deleteFolder(Principal principal,
	                               @RequestParam(value = "folderId") Optional<Long> folderId,
	                               @RequestParam(value = "deleteFolderId") Long deleteFolderId) {
	        folderService.deleteFolder(deleteFolderId, principal.getName());
	        return folderId.map(aLong -> "redirect:/home?folderId=" + aLong).orElse("redirect:/home");
	    }

	    @PostMapping("/prevPage")
	    public String previousPage(Principal principal,
	                               @RequestParam(value = "folderId") Optional<Long> folderId) {
	        Long prevFolderId = folderService.getPrevFolderId(folderId, principal.getName());
	        if (prevFolderId != null) {
	            return "redirect:/home?folderId=" + prevFolderId;
	        } else {
	            return "redirect:/home";
	        }
	    }

	    @PostMapping("/SharedFolder/prevPage")
	    public String sharedFolderPreviousPage(Principal principal,
	                                           @RequestParam(value = "folderId") Optional<Long> folderId) {
	        Long previousFolderId = folderService.getPrevFolderId(folderId, principal.getName());
	        if (previousFolderId != null) {
	            return "redirect:/SharedWithMe/?folderId=" + previousFolderId;
	        } else {
	            return "redirect:/SharedWithMe";
	        }
	    }

	    @RequestMapping(value = "/downloadFolder/{folderId}", produces = "application/zip")
	    public ResponseEntity<StreamingResponseBody> downloadFolder(@PathVariable Long folderId) {
	    	
	        return folderService.downloadFolder(folderId);
	    }

	    @RequestMapping(value = "/downloadSharedFolder/{randomId}")
	    public ResponseEntity<StreamingResponseBody> downloadRandomFolder(@PathVariable("randomId") long randomId) {
	        return folderService.downloadSharedFolder(randomId);
	    }

}
