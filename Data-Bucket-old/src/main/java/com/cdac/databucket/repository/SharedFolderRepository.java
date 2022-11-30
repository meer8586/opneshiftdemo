package com.cdac.databucket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.databucket.entity.SharedFolder;

@Repository
public interface SharedFolderRepository extends JpaRepository<SharedFolder, Long>{
	  @Query(value = "select * from shared_folders where user_user_id=?1", nativeQuery = true)
	    List<SharedFolder> getAllByUser(long id);
}
