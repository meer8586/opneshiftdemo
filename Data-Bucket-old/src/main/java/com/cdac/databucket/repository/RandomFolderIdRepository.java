package com.cdac.databucket.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdac.databucket.entity.RandomFolderId;

public interface RandomFolderIdRepository extends JpaRepository<RandomFolderId, Long> {
    @Query(value = "select * from random_folder_id where folder_folder_id=?1 limit 1", nativeQuery = true)
    RandomFolderId getByFolder(long id);
}
