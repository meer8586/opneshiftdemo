package com.cdac.databucket.specification;


import org.springframework.data.jpa.domain.Specification;

import com.cdac.databucket.entity.File;

public interface FileSpecification {
    Specification<File> insideFolder(Integer folderId);

    Specification<File> withUser(String userEmail);

    Specification<File> search(String search);
}
