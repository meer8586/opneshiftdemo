package com.cdac.databucket.demo.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.databucket.repository.UserRepository;
import com.cdac.databucket.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepo;
	
	
	private UserServiceImpl userServ;
	
	@BeforeEach
	void setUp() {
		this.userServ= new UserServiceImpl(this.userRepo);
	}
	
	void getAllUsers()
	{
		userServ.findAllUsers();	
		verify(userRepo).findAll();				
	}	
	
}
