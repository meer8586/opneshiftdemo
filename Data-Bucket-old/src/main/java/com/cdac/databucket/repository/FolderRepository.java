package com.cdac.databucket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cdac.databucket.entity.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long>, JpaSpecificationExecutor<Folder> {

	
	
}
