package com.cdac.databucket.specification;


import org.springframework.data.jpa.domain.Specification;

import com.cdac.databucket.entity.Folder;

public interface FolderSpecification {
    Specification<Folder> insideFolder(Integer folderId);

    Specification<Folder> withUser(String userEmail);

    Specification<Folder> search(String search);
}
