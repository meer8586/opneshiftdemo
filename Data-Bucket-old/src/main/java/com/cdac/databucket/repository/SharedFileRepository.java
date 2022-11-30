package com.cdac.databucket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.databucket.entity.SharedFile;


@Repository
public interface SharedFileRepository extends JpaRepository<SharedFile, Long> {
	 @Query(value = "select * from shared_files where user_user_id=?1", nativeQuery = true)
	    List<SharedFile> getAllByUser(long id);
}
