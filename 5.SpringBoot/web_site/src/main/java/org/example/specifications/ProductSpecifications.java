package org.example.specifications;

import jakarta.persistence.criteria.Predicate;
import org.example.data.data_transfer_objects.search.ProductSearchDTO;
import org.example.entities.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<ProductEntity> search(ProductSearchDTO s) {
        return (root, query, cb) -> {
            Predicate p = cb.conjunction();

            if (s.getName() != null && !s.getName().isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("name")),
                        "%" + s.getName().toLowerCase() + "%"));
            }

            if (s.getSlug() != null && !s.getSlug().isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("slug")),
                        "%" + s.getSlug().toLowerCase() + "%"));
            }

            if (s.getDescription() != null && !s.getDescription().isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("description")),
                        "%" + s.getDescription().toLowerCase() + "%"));
            }

            if (s.getMinPrice() != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("price"), s.getMinPrice()));
            }

            if (s.getMaxPrice() != null) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("price"), s.getMaxPrice()));
            }

            if (s.getCategoryId() != null) {
                p = cb.and(p, cb.equal(root.get("category").get("id"), s.getCategoryId()));
            }

            return p;
        };
    }
}
