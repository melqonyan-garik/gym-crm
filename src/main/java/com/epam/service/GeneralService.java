package com.epam.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public abstract class GeneralService {

    @PersistenceContext
    protected EntityManager entityManager;

    protected void addLikePredicate(Path<String> path, String value, CriteriaBuilder cb, List<Predicate> predicates) {
        if (StringUtils.isNotEmpty(value)) {
            predicates.add(cb.like(path, value));
        }
    }

    protected void addLessThanOrEqualsPredicate(Path<LocalDate> path, LocalDate value, CriteriaBuilder cb, List<Predicate> predicates) {
        if (value != null) {
            predicates.add(cb.lessThanOrEqualTo(path, value));
        }
    }
    protected void addGreaterThanOrEqualsPredicate(Path<LocalDate> path, LocalDate value, CriteriaBuilder cb, List<Predicate> predicates) {
        if (value != null) {
            predicates.add(cb.greaterThanOrEqualTo(path, value));
        }
    }

}

