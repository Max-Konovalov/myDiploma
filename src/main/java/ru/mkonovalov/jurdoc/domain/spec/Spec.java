package ru.mkonovalov.jurdoc.domain.spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Function;

@UtilityClass
public class Spec {

    public <E> Specification<E> startsWith(SingularAttribute<E, String> stringAttribute, String value) {
        return value.isEmpty()
                ? alwaysFalse() :
                like(root -> root.get(stringAttribute), value + "%");
    }

    public <E> Specification<E> startsWith(Function<Root<E>, Path<String>> attributeFunc, String value) {
        return value.isEmpty()
                ? alwaysFalse() :
                like(attributeFunc, value + "%");
    }

    public <E> Specification<E> contains(SingularAttribute<E, String> stringAttribute, String value) {
        return value.isEmpty()
                ? alwaysFalse() :
                like(root -> root.get(stringAttribute), "%" + value + "%");
    }

    public <E> Specification<E> contains(Function<Root<E>, Path<String>> attributeFunc, String value) {
        return value.isEmpty()
                ? alwaysFalse() :
                like(attributeFunc, "%" + value + "%");
    }

    private <E> Specification<E> like(Function<Root<E>, Path<String>> attributeFunc, String value) {
        return (root, query, cb) ->
                cb.like(cb.lower(attributeFunc.apply(root)), value.toLowerCase());
    }

    public <E> Specification<E> alwaysFalse() {
        return (root, query, cb) -> cb.disjunction();
    }

    public <E> Specification<E> alwaysTrue() {
        return (root, query, cb) -> cb.conjunction();
    }

    public <E, F> Specification<E> fieldIn(SingularAttribute<E, F> attribute,
                                           Collection<F> values) {
        return values.isEmpty() ? alwaysFalse() : (root, query, cb) -> root.get(attribute).in(values);
    }

    public <E, F> Specification<E> fieldEqual(SingularAttribute<E, F> attribute,
                                              F value) {
        return (root, query, cb) -> cb.equal(root.get(attribute), value);
    }

    public <E, F> Specification<E> fieldEqual(Function<Root<E>, Path<F>> fieldPathFunc,
                                              F filedValue) {
        return (root, query, cb) -> cb.equal(fieldPathFunc.apply(root), filedValue);
    }

    public <E, F> Specification<E> fieldNotEqual(SingularAttribute<E, F> attribute,
                                                 F filedValue) {
        return (root, query, cb) -> cb.notEqual(root.get(attribute), filedValue);
    }

    public <E, F> Specification<E> fieldNotEqual(SingularAttribute<E, F> a, SingularAttribute<E, F> b) {
        return (root, query, cb) -> cb.notEqual(root.get(a), root.get(b));
    }

    public <E, F> Specification<E> isNotNull(SingularAttribute<E, F> attribute) {
        return (root, query, cb) -> cb.isNotNull(root.get(attribute));
    }

    public <E, F> Specification<E> isNull(SingularAttribute<E, F> attribute) {
        return (root, query, cb) -> cb.isNull(root.get(attribute));
    }

    public <E> Specification<E> after(SingularAttribute<E, LocalDateTime> attribute,
                                      LocalDateTime date) {
        return (root, query, cb) -> cb.greaterThan(root.get(attribute), date);
    }

    public <E> Specification<E> after(SingularAttribute<E, LocalDate> attribute, LocalDate date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), date);
    }

    public <E> Specification<E> before(SingularAttribute<E, LocalDateTime> attribute,
                                       LocalDateTime date) {
        return (root, query, cb) -> cb.lessThan(root.get(attribute), date);
    }

    public <E> Specification<E> before(SingularAttribute<E, LocalDate> attribute, LocalDate date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), date);
    }

    public <E, F extends Number> Specification<E> lessThanOrEqual(
            SingularAttribute<E, F> attribute,
            F value) {
        return (root, query, cb) -> cb.le(root.get(attribute), value);
    }

    public <E, F extends Number> Specification<E> greaterThanOrEqual(
            SingularAttribute<E, F> attribute,
            F value) {
        return (root, query, cb) -> cb.ge(root.get(attribute), value);
    }

    public boolean isCountQuery(CriteriaQuery<?> query) {
        return query.getResultType() == Long.class || query.getResultType() == long.class;
    }

    public <E> Specification<E> fetchInner(SingularAttribute<E, ?> singularAttribute) {
        return fetch(singularAttribute, JoinType.INNER);
    }

    public <E> Specification<E> fetchLeft(SingularAttribute<E, ?> singularAttribute) {
        return fetch(singularAttribute, JoinType.LEFT);
    }

    public <E> Specification<E> fetchLeft(PluralAttribute<E, ?, ?> pluralAttribute) {
        return (root, query, cb) -> {
            if (!isCountQuery(query)) {
                root.fetch(pluralAttribute, JoinType.LEFT);
                query.distinct(true);
            }
            return null;
        };
    }

    public <E> Specification<E> fetch(SingularAttribute<E, ?> attribute, JoinType joinType) {
        return (root, query, cb) -> {
            if (!isCountQuery(query)) {
                root.fetch(attribute, joinType);
                query.distinct(true);
            }
            return null;
        };
    }
}
