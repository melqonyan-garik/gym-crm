package com.epam.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
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

