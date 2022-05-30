package ru.mkonovalov.jurdoc.domain.spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.mkonovalov.jurdoc.domain.entity.Document;
import ru.mkonovalov.jurdoc.domain.entity.DocumentPackage_;
import ru.mkonovalov.jurdoc.domain.entity.Document_;
import ru.mkonovalov.jurdoc.domain.entity.User_;

import java.time.LocalDateTime;

@UtilityClass
public class DocumentSpec {
    public Specification<Document> nameLike(String nameLike) {
        return nameLike.isEmpty()
                ? (root, query, cb) -> cb.disjunction()
                : (root, query, cb) -> cb.like(root.get(Document_.name),
                "%" + nameLike.toLowerCase().trim() + "%");
    }

    public Specification<Document> createdAtFrom(LocalDateTime time) {
        return Spec.after(Document_.createdAt, time);
    }

    public Specification<Document> createdAtTo(LocalDateTime time) {
        return Spec.before(Document_.createdAt, time);
    }

    public Specification<Document> extensionLike(String extensionLike) {
        return extensionLike.isEmpty()
                ? (root, query, cb) -> cb.disjunction()
                : (root, query, cb) -> cb.like(root.get(Document_.extension),
                "%" + extensionLike.toLowerCase().trim() + "%");
    }

    public Specification<Document> createdByEq(Long createdBy) {
        if (createdBy != null) {
            return ((root, query, cb) -> cb.equal(root.get(Document_.createdBy).get(User_.ID), createdBy));
        }

        return ((root, query, criteriaBuilder) -> criteriaBuilder.disjunction());
    }

    public Specification<Document> documentPackageEq(Long docPackage) {
        if (docPackage != null) {
            return ((root, query, cb) -> cb.equal(root.get(Document_.documentPackage).get(DocumentPackage_.ID), docPackage));
        }

        return ((root, query, criteriaBuilder) -> criteriaBuilder.disjunction());
    }
}
