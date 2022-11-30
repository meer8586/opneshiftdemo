package com.cdac.databucket.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.databucket.entity.User;
import com.cdac.databucket.repository.UserRepository;
import com.cdac.databucket.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final String USER_ROLE = "ROLE_USER";
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		user.setRole(USER_ROLE);
		return userRepository.save(user);
	}

	@Override
	public List<String> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.getAllUsers();
	}

	public UserServiceImpl(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}

	
	
}
