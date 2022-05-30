package ru.mkonovalov.jurdoc.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Helps to createToken {@link Specification} for some filter (object with multiple filter values).
 * Holds a specification building rule for each filter field.
 * <p>
 * Usage:
 * <pre>
 * {@code
 * var processor = FilterProcessor.
 *   strict(UsersFilter::getId, UserSpec::idEq).
 *   strict(UsersFilter::getLogin, UserSpec::loginEqual).
 *   relaxed(UsersFilter::getLoginLike, (filter) -> UserSpec.loginLike(filter.getLogin(), Case.INSENSITIVE).
 *   build();
 *
 * Specification<User> spec = processor.buildSpec(filter);
 * }
 * </pre>
 *
 * @param <F> filter type
 * @param <E> entity type
 */
public class FilterProcessor<F, E> {

    private final Collection<Rule<F, E>> rules;

    /**
     * Use {@link FilterProcessor#strict(Function, Function)} or
     * {@link FilterProcessor#relaxed(Function, Function)} methods to build an instance.
     */
    private FilterProcessor(Collection<Rule<F, E>> rules) {
        this.rules = Collections.unmodifiableCollection(rules);
    }

    /**
     * Strict rule to link filter getter and specification building method. Specification building method
     * should consume strictly one argument matching the getter return type.
     *
     * @param getter      filter getter method
     * @param specBuilder specification building method. Must consume strictly one argument matching the getter
     *                    return type
     * @param <F>         filter type
     * @param <E>         entity type
     * @param <T>         getter return type
     * @return {@link FilterProcessor} builder
     */
    public static <F, E, T> Builder<F, E>
    strict(Function<F, T> getter, Function<T, Specification<E>> specBuilder) {
        return new Builder<F, E>().strict(getter, specBuilder);
    }

    /**
     * Relaxed rule to link filter getter and specification building lambda.
     *
     * @param getter      filter getter
     * @param specCreator spec building function
     * @param <F>         filter type
     * @param <E>         entity type
     * @return {@link FilterProcessor} builder
     */
    public static <F, E> Builder<F, E>
    relaxed(Function<F, ?> getter, Function<F, Specification<E>> specCreator) {
        return new Builder<F, E>().relaxed(getter, specCreator);
    }

    /**
     * Builds {@link Specification} for filter instance
     *
     * @param filter filter
     * @return built specification
     */
    public Specification<E> buildSpec(F filter) {
        Specification<E> spec = Specification.where(null);
        for (Rule<F, E> rule : rules) {
            if (rule.notEmpty(filter)) {
                spec = spec.and(rule.buildSpec(filter));
            }
        }
        return spec;
    }


    public static class Builder<F, E> {
        private final List<Rule<F, E>> rules = new ArrayList<>();

        /**
         * See {@linkplain FilterProcessor#relaxed(Function, Function)}
         */
        public Builder<F, E> relaxed(Function<F, ?> getter, Function<F, Specification<E>> specCreator) {
            rules.add(new RelaxedRule<>(getter, specCreator));
            return this;
        }

        /**
         * See {@linkplain FilterProcessor#strict(Function, Function)}
         */
        public <T> Builder<F, E> strict(Function<F, T> getter, Function<T, Specification<E>> specBuilder) {
            rules.add(new StrictRule<>(getter, specBuilder));
            return this;
        }

        /**
         * @return Built {@link FilterProcessor}
         */
        public FilterProcessor<F, E> build() {
            return new FilterProcessor<>(rules);
        }
    }

    private interface Rule<F, E> {

        boolean notEmpty(F filter);

        Specification<E> buildSpec(F filter);
    }

    private static class StrictRule<F, E, T> implements Rule<F, E> {
        private final Function<F, T> getter;
        private final Function<T, Specification<E>> specBuilder;

        private StrictRule(Function<F, T> getter, Function<T, Specification<E>> specBuilder) {
            this.getter = getter;
            this.specBuilder = specBuilder;
        }

        @Override
        public boolean notEmpty(F filter) {
            return getter.apply(filter) != null;
        }

        @Override
        public Specification<E> buildSpec(F filter) {
            T value = getter.apply(filter);
            return specBuilder.apply(value);
        }
    }

    private static class RelaxedRule<F, E> implements Rule<F, E> {
        private final Function<F, ?> getter;
        private final Function<F, Specification<E>> specCreator;

        private RelaxedRule(Function<F, ?> getter, Function<F, Specification<E>> specCreator) {
            this.getter = getter;
            this.specCreator = specCreator;
        }

        @Override
        public boolean notEmpty(F filter) {
            return getter.apply(filter) != null;
        }

        @Override
        public Specification<E> buildSpec(F filter) {
            return specCreator.apply(filter);
        }
    }
}

