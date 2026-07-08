package com.dayma.specification;

import com.dayma.dto.ProductFiltersRequest;
import com.dayma.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withFilters(ProductFiltersRequest filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.categories() != null && !filters.categories().isBlank()) {
                String[] cats = filters.categories().split(",");
                Join<Product, Category> categoryJoin = root.join("category");
                predicates.add(categoryJoin.get("code").in((Object[]) cats));
            }

            if (filters.sizes() != null && !filters.sizes().isBlank()) {
                String[] cols = filters.sizes().split(",");
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<ProductSize> pcRoot = subquery.from(ProductSize.class);
                subquery.select(pcRoot.get("size").get("id"));
                Join<ProductSize, Size> sizeJoin = pcRoot.join("size");
                subquery.where(sizeJoin.get("code").in((Object[]) cols));
                predicates.add(root.get("id").in(subquery));
            }

            if (filters.collection() != null && !filters.collection().isBlank()) {
                String[] cols = filters.collection().split(",");
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<ProductCollection> pcRoot = subquery.from(ProductCollection.class);
                subquery.select(pcRoot.get("product").get("id"));
                Join<ProductCollection, Collection> collectionJoin = pcRoot.join("collection");
                subquery.where(collectionJoin.get("code").in((Object[]) cols));
                predicates.add(root.get("id").in(subquery));
            }

            if (filters.minPrice() != null && !filters.minPrice().isBlank()) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"),
                        Double.parseDouble(filters.minPrice())));
            }

            if (filters.maxPrice() != null && !filters.maxPrice().isBlank()) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"),
                        Double.parseDouble(filters.maxPrice())));
            }

            if (filters.name() != null && !filters.name().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")),
                        "%" + filters.name().toLowerCase() + "%"));
            }

            if (filters.code() != null && !filters.code().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("code")),
                        "%" + filters.code().toLowerCase() + "%"));
            }

            if (filters.archived() != null) {
                predicates.add(cb.equal(root.get("archived"), filters.archived()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
