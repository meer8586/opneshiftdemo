package com.cdac.databucket.controller;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdac.databucket.entity.File;
import com.cdac.databucket.entity.Folder;
import com.cdac.databucket.entity.User;
import com.cdac.databucket.service.FileService;
import com.cdac.databucket.service.FolderService;
import com.cdac.databucket.service.SharingService;
import com.cdac.databucket.service.UserService;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;


@Controller
@RequestMapping("/share")
public class SharingController {

	
	 @Autowired
	    private SharingService sharingService;

	    @Autowired
	    private FolderService folderService;

	    @Autowired
	    private FileService fileService;

	    @Autowired
	    private EmailService emailService;

	    @Autowired
	    private UserService userService;

	    @GetMapping("/fileForm/{id}")
	    public String showForm(@PathVariable("id") long fileId, Model model) {
	        Optional<File> file = fileService.findById(fileId);
	        model.addAttribute("file", file.get());
	        return "share-file";
	    }

	    @GetMapping("/folderForm/{id}")
	    public String showFormFolder(@PathVariable("id") long folderId, Model model) {
	        Optional<Folder> folder = folderService.findById(folderId);
	        model.addAttribute("folder", folder.get());
	        return "share-folder";
	    }

	    @PostMapping("/file/{fileId}")
	    public String shareFile(@PathVariable("fileId") long fileId, @RequestParam("user") String email,
	                            Model model) {
	        Optional<User> user = userService.findByEmail(email);
	        if (user.isPresent()) {
	            sharingService.shareFile(fileId, email);
	            return "redirect:/home";
	        } else {
	            Optional<File> file = fileService.findById(fileId);
	            model.addAttribute("file", file.get());
	            return "share-file";
	        }
	    }

	    @PostMapping("/folder/{folderId}") 
	    public String shareFolder(@PathVariable("folderId") long folderId, @RequestParam("user") String email,
	                              Model model) {
	        Optional<User> user = userService.findByEmail(email);
	        System.out.println(folderId);
	        if (user.isPresent()) {
	            sharingService.shareFolder(folderId, email);
	        } else {
	            System.out.println("\nUser not found\n");
	        }
        return "redirect:/home";
	    }
	
	       
	    @GetMapping("/ViaEmail/{id}")
	    public String shareViaEmail(@PathVariable("id") long fileId, Model model) {
	        model.addAttribute("fileId", fileId);
	        return "share-file-via-mail";
	    }

	    @GetMapping("/folder-via-email/{id}")
	    public String shareFolderForm(Model model, @PathVariable long id) {
	        model.addAttribute("folderId", id);
	        return "share-folder-via-mail";
	    }

	    @PostMapping("/folder-via-email")
	    public String shareFolderViaEmail(@RequestParam("folderId") long id,
	                                      @RequestParam("email") String emails
	                                      ) {
	        String url = folderService.shareViaEmail(id);
	        try {
	            List<InternetAddress> listOfEmails = Arrays.stream(emails.split(";")).map(mail -> {
	                try {
	                    return new InternetAddress(mail.trim());
	                } catch (AddressException e) {
	                    e.printStackTrace();
	                }
	                return null;
	            }).collect(Collectors.toList());
	            System.out.println("\n Sending Email \nURL:" + url + "\nTo:" + listOfEmails);
	            final Email email = DefaultEmail.builder()
	                    .from(new InternetAddress("databucket777@gmail.com"))
	                    .replyTo(new InternetAddress("shivkumarswami50gmail.com"))
	                    .to(listOfEmails)
	                    .subject("Shared a folder via DataBucket")
	                    .body(url)
	                    .encoding(String.valueOf(Charset.forName("UTF-8"))).build();

	            emailService.send(email);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "redirect:/home";
	    }
	    	    
	    
}
