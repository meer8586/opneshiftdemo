package com.cdac.databucket.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.databucket.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long>, JpaSpecificationExecutor<File> {

	@Modifying
	@Transactional
	@Query(value = "SELECT file.id from File as file where file.folder.id = :folderId")
	List<Long> findFileIdsByFolderId(Long folderId);

	
	
}
