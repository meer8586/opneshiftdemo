package com.cdac.databucket.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.databucket.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByEmail(String username);
	
//	@Query(value = "select password from users where email=?1", nativeQuery = true)
//	User findByEmail1(String usernames);
	
	@Query(value = "update users set password=?1 where username=?2", nativeQuery = true)
	void setPass(String userpass, String username);
	
	
	@Query(value="select * from users",nativeQuery = true)
	List<String> getAllUsers();
}
