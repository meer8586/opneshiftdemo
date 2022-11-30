package com.cdac.databucket.service;

import java.util.*;

import com.cdac.databucket.entity.User;

public interface UserService {

	 Optional<User> findByEmail(String email);
	 User save(User user);
	 
	 List<String> findAllUsers();
	
}
