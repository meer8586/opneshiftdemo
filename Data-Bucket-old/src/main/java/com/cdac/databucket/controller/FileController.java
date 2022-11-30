package com.cdac.databucket.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.databucket.entity.File;
import com.cdac.databucket.entity.Folder;
import com.cdac.databucket.entity.SharedFile;
import com.cdac.databucket.entity.SharedFolder;
import com.cdac.databucket.entity.User;
import com.cdac.databucket.repository.SharedFileRepository;
import com.cdac.databucket.repository.SharedFolderRepository;
import com.cdac.databucket.service.FileService;
import com.cdac.databucket.service.FolderService;
import com.cdac.databucket.service.SharingService;

import com.cdac.databucket.service.UserService;


//@RestController
//@CrossOrigin("http://localhost:3000")
@Controller
public class FileController {

	@Autowired
	private FileService fileService;
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private SharingService sharingService;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private SharedFileRepository sharedFileRepository;
	
	@Autowired
	private SharedFolderRepository sharedFolderRepository;
	
	@PostMapping("/upload")
	public String AddFile(Principal principal, @RequestParam(value = "folderId") Optional<Long> folderId,
			@RequestParam(value = "file") MultipartFile file) {
		
		fileService.saveFile(folderId, file, principal.getName());
		
		return (String) folderId.map(aLong -> "redirect:/home?folderId=" + aLong).orElse("redirect:/home");
	}
	 @GetMapping("/home")
	    public String listFilesAndFolders(Principal principal,
	                                      Model model,
	                                      
	                                      @RequestParam Optional<Integer> folderId,
	                                      @RequestParam Optional<String> search) {
	        List<File> files = fileService.findFilesInsideFolders(folderId,  principal.getName(), search);
	        List<Folder> currentFolders = folderService.findFoldersInsideFolder(folderId, principal.getName(), search);
	        model.addAttribute("folders", currentFolders);
	        model.addAttribute("files", files);
	        model.addAttribute("currentuser", principal.getName());
	        return "home";
	    }
	
	 @PostMapping("/delete/{fileId}")
	    public String deleteFile(Principal principal,
	                             @RequestParam(value = "folderId") Optional<Long> folderId,
	                             @PathVariable Long fileId) {
	        fileService.deleteFile(fileId, principal.getName());
	        return folderId.map(aLong -> "redirect:/home?folderId=" + aLong).orElse("redirect:/home");
	    }
	 
	 @GetMapping("/download/{fileId}")
	    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId) {
	        Optional<File> file = fileService.findById(fileId);
	        byte[] data = fileService.downloadFile(fileId);
	        ByteArrayResource resource = new ByteArrayResource(data);
	        return ResponseEntity
	                .ok()
	                .contentLength(data.length)
	                .header("Content-type", "application/octet-stream")
	                .header("Content-disposition", "attachment; filename=\"" + file.get().getFileName() + "\"")
	                .body(resource);
	    }
	 
	 @PostMapping("/share/{fileId}")
	    public String shareForm(@PathVariable("fileId") long fileId, Model model) {
	        Optional<File> file = fileService.findById(fileId);
	        model.addAttribute("file", file.get());
	        List<String> users = userService.findAllUsers();
	        model.addAttribute("users", users);
	        return "share-file";
	    }
	 
	 @GetMapping("/SharedWithMe")
	    public String sharedWithMe(Principal principal, Model model,
	                               @RequestParam Optional<String> order,
	                               @RequestParam Optional<Integer> folderId,
	                               @RequestParam Optional<String> search) {
	        String email = principal.getName();
	        Optional<User> user = userService.findByEmail(email);
	        if (!folderId.isPresent()) {
	            List<File> files = sharingService.getAllShareFilesByUser(user.get().getId());
	            List<Folder> folders = sharingService.getAllShareFoldersByUser(user.get().getId());
	            model.addAttribute("folders", folders);
	            model.addAttribute("files", files);
	        } else {
	            long id = folderId.get();
	            Optional<Folder> folder = folderService.findById(id);
	            List<File> files = folder.get().getFiles();
	            Set<Folder> folders = folder.get().getChilds();
	            model.addAttribute("folders", folders);
	            model.addAttribute("files", files);
	        }
	        return "shared-with-me";
	    }
	 
	 @PostMapping("/shareFolder")
	    public String shareFolderForm(@RequestParam("shareFolderId") long shareFolderId,
	                                  @RequestParam("folderId") Optional<Long> folderId, Model model) {
	        Optional<Folder> folder = folderService.findById(shareFolderId);
	        model.addAttribute("folder", folder.get());
	        List<String> users = userService.findAllUsers();
	        model.addAttribute("users", users);
	        return "share-folder";
	    }
	 
	 @GetMapping("/SharedByMe")
	    public String sharedByMe(Model model, Principal principal) {
	        Optional<User> user = userService.findByEmail(principal.getName());
	        List<SharedFolder> allSharedFolders = sharedFolderRepository.findAll();
	        List<SharedFolder> sharedFolders = new ArrayList<>();
	        for (SharedFolder sharedFolder : allSharedFolders) {
	            if (sharedFolder.getFolder().getUser().getId() == user.get().getId()) {
	                sharedFolders.add(sharedFolder);
	            }
	        }
	        List<SharedFile> allSharedFiles = sharedFileRepository.findAll();
	        List<SharedFile> sharedFiles = new ArrayList<>();
	        for (SharedFile sharedFile : allSharedFiles) {
	            if (sharedFile.getFile().getUser().getId() == user.get().getId()) {
	                sharedFiles.add(sharedFile);
	            }
	        }
	        model.addAttribute("files", sharedFiles);
	        model.addAttribute("folders", sharedFolders);
	        return "shared-by-me";
	    }
}
