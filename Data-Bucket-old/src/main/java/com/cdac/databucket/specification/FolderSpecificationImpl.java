package com.cdac.databucket.specification;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cdac.databucket.entity.Folder;

@Component
public class FolderSpecificationImpl implements FolderSpecification {

    @Override
    public Specification<Folder> insideFolder(Integer folderId) {
        return (((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            if (folderId == null) {
                return criteriaBuilder.and(criteriaBuilder.isNull(root.get("parent")));
            } else {
                return criteriaBuilder.and(criteriaBuilder.equal(root.join("parent").get("id"), folderId));
            }
        }));
    }

    @Override
    public Specification<Folder> withUser(String userEmail) {
        return (((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            if (userEmail == null) {
                return null;
            }
            return criteriaBuilder.and(criteriaBuilder.equal(root.join("user").get("email"), userEmail));
        }));
    }

    @Override
    public Specification<Folder> search(String search) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            if (search == null) {
                return null;
            }
            return criteriaBuilder.and(criteriaBuilder.like(root.get("folderName"), "%" + search + "%"));
        });
    }
}
