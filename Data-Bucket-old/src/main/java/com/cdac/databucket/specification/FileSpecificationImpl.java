package com.cdac.databucket.specification;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cdac.databucket.entity.File;

@Component
public class FileSpecificationImpl implements FileSpecification {

    @Override
    public Specification<File> insideFolder(Integer folderId) {
        return (((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            if (folderId == null) {
                return criteriaBuilder.and(criteriaBuilder.isNull(root.get("folder")));
            } else {
                return criteriaBuilder.and(criteriaBuilder.equal(root.join("folder").get("id"), folderId));
            }
        }));
    }

    @Override
    public Specification<File> withUser(String userEmail) {
        return (((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            if (userEmail == null) {
                return null;
            }
            return criteriaBuilder.and(criteriaBuilder.equal(root.join("user").get("email"), userEmail));
        }));
    }

    @Override
    public Specification<File> search(String search) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            if (search == null) {
                return null;
            }
            return criteriaBuilder.and(criteriaBuilder.like(root.get("fileName"), "%" + search + "%"));
        });
    }

}
