package ru.mkonovalov.jurdoc.domain.spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.mkonovalov.jurdoc.domain.entity.ERole;
import ru.mkonovalov.jurdoc.domain.entity.Role_;
import ru.mkonovalov.jurdoc.domain.entity.User;
import ru.mkonovalov.jurdoc.domain.entity.User_;

@UtilityClass
public class UserSpec {
    public Specification<User> roleIs(String role) {
        if (role != null) {
            return ((root, query, cb) -> cb.equal(root.get(User_.role).get(Role_.NAME), ERole.getByName(role)));
        }

        return ((root, query, criteriaBuilder) -> criteriaBuilder.disjunction());
    }

    public Specification<User> usernameLike(String usernameLike) {
        return usernameLike.isEmpty()
                ? (root, query, cb) -> cb.disjunction()
                : (root, query, cb) -> cb.like(root.get(User_.username),
                "%" + usernameLike.toLowerCase().trim() + "%");
    }

    public Specification<User> emailLike(String emailLike) {
        return emailLike.isEmpty()
                ? (root, query, cb) -> cb.disjunction()
                : (root, query, cb) -> cb.like(root.get(User_.email),
                "%" + emailLike.toLowerCase().trim() + "%");
    }

    public Specification<User> firstNameLike(String firstNameLike) {
        return firstNameLike.isEmpty()
                ? (root, query, cb) -> cb.disjunction()
                : (root, query, cb) -> cb.like(root.get(User_.firstName),
                "%" + firstNameLike.toLowerCase().trim() + "%");
    }

    public Specification<User> lastNameLike(String lastNameLike) {
        return lastNameLike.isEmpty()
                ? (root, query, cb) -> cb.disjunction()
                : (root, query, cb) -> cb.like(root.get(User_.lastName),
                "%" + lastNameLike.toLowerCase().trim() + "%");
    }

    public Specification<User> middleNameLike(String middleNameLike) {
        return middleNameLike.isEmpty()
                ? (root, query, cb) -> cb.disjunction()
                : (root, query, cb) -> cb.like(root.get(User_.middleName),
                "%" + middleNameLike.toLowerCase().trim() + "%");
    }
}
