package com.cdac.databucket.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.databucket.entity.File;
import com.cdac.databucket.entity.User;
import com.cdac.databucket.repository.UserRepository;
import com.cdac.databucket.service.UserService;

//@RestController
//@CrossOrigin("http://localhost:3000")
@Controller
public class SignUpController {

	@Autowired
	 private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	

			@GetMapping("/login")
		    public String login(Principal principal) {
		        if (principal != null) {
		            return "redirect:/home";
		        }
		        return "login";
		    }


	    @RequestMapping(value = "/signup", method = RequestMethod.GET)
	    public String registration(Model model) {

	        model.addAttribute("user", new User());
	        return "signup";
	    }

	    @RequestMapping(value = "/signup", method = RequestMethod.POST)
	    public String createNewUser(@Valid User user,
	                                BindingResult bindingResult,
	                                Model model
)  {

	        if (userService.findByEmail(user.getEmail()).isPresent()) {
	            bindingResult.rejectValue("email", "error.user",
	                    "There is already a user registered with the email provided");
	        }

	        if (!bindingResult.hasErrors()) {
	        	user.setPassword(this.bcryptPasswordEncoder.encode(user.getPassword()));
	            userService.save(user);
	            model.addAttribute("successMessage", "Success");
	                   
	            model.addAttribute("user", new User());
	            
	        }
	       
	        
	        return "signup";
	    }

	    @GetMapping("/")
	    public String returnLoginPage(Model model, Principal principal) {
	        if(principal==null) {
	            model.addAttribute("user", new User());
	            return "index";
	        } else {
	            return "redirect:/home";
	        }
	    }
	
	    
	 
	 	    
	    @GetMapping("/contact")
	    public String contact() {
	        
	            return "contact";
	       
	    }
	
	    @GetMapping("/about")
	    public String about() {
	        
	            return "about";
	       
	    }
}
