package ru.practicum.event.repository;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.Event_;
import ru.practicum.event.model.SortType;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.ParticipationRequest_;
import ru.practicum.request.model.RequestStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Repository
@RequiredArgsConstructor
class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @NotNull
    public List<Event> searchEvents(
            @Nullable List<Long> users,
            @Nullable List<EventState> states,
            @Nullable List<Long> categories,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @NotNull Pageable pageable
    ) {
        return pageableQuery(pageable, (cb, cq) -> {
            var root = cq.from(Event.class);
            var predicates = new ArrayList<Predicate>();
            if (users != null) {
                predicates.add(root.get(Event_.initiator).in(users));
            }
            withStateIn(states, predicates, root);
            withCategoryIn(categories, predicates, root);
            withEventDateBetween(rangeStart, rangeEnd, predicates, root, cb);
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        });
    }

    @Override
    @NotNull
    public List<Event> getEvents(
            @Nullable String query,
            @Nullable List<Long> categories,
            @Nullable Boolean paid,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @Nullable Boolean onlyAvailable,
            @Nullable SortType sort,
            @NotNull Pageable pageable
    ) {
        return pageableQuery(pageable, (cb, cq) -> {
            var root = cq.from(Event.class);
            var predicates = new ArrayList<Predicate>();
            if (query != null) {
                var pattern = "%" + query.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get(Event_.annotation)), pattern),
                        cb.like(cb.lower(root.get(Event_.description)), pattern)
                ));
            }
            if (paid != null) {
                predicates.add(cb.equal(root.get(Event_.paid), paid));
            }
            if (Boolean.TRUE.equals(onlyAvailable)) {
                var limit = root.get(Event_.limit).as(Long.class);
                var sub = cq.subquery(Long.class);
                var subRoot = sub.from(ParticipationRequest.class);
                var subEvent = subRoot.join(ParticipationRequest_.event);
                sub.select(cb.count(subRoot.get(ParticipationRequest_.id)));
                sub.where(cb.and(
                        cb.equal(root.get(Event_.id), subEvent.get(Event_.id)),
                        cb.equal(subRoot.get(ParticipationRequest_.status), RequestStatus.CONFIRMED)
                ));
                predicates.add(cb.lessThan(sub, limit));
            }
            withStateIn(List.of(EventState.PUBLISHED), predicates, root);
            withCategoryIn(categories, predicates, root);
            withEventDateBetween(rangeStart, rangeEnd, predicates, root, cb);
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
            if (sort != null) {
                switch (sort) {
                    case EVENT_DATE:
                        cq.orderBy(cb.desc(root.get(Event_.eventDate)));
                        break;
                    case VIEWS:
                        // TODO реализовать
                        break;
                }
            }
        });
    }

    private List<Event> pageableQuery(@NotNull Pageable pageable, @NotNull BiConsumer<CriteriaBuilder, CriteriaQuery<Event>> queryBuilder) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(Event.class);
        queryBuilder.accept(cb, cq);
        return entityManager.createQuery(cq)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    private static void withStateIn(@Nullable List<EventState> states, @NotNull ArrayList<Predicate> predicates, @NotNull Root<Event> root) {
        if (states != null) {
            predicates.add(root.get(Event_.state).in(states));
        }
    }

    private static void withCategoryIn(@Nullable List<Long> categories, @NotNull ArrayList<Predicate> predicates, @NotNull Root<Event> root) {
        if (categories != null) {
            predicates.add(root.<Long>get(Event_.CATEGORY).in(categories));
        }
    }

    private static void withEventDateBetween(@Nullable LocalDateTime rangeStart, @Nullable LocalDateTime rangeEnd, @NotNull ArrayList<Predicate> predicates, @NotNull Root<Event> root, @NotNull CriteriaBuilder builder) {
        if (rangeStart != null || rangeEnd != null) {
            var eventDate = root.get(Event_.eventDate);
            Predicate predicate;
            if (rangeEnd == null) {
                predicate = builder.lessThanOrEqualTo(eventDate, rangeStart);
            } else if (rangeStart == null) {
                predicate = builder.greaterThanOrEqualTo(eventDate, rangeEnd);
            } else {
                predicate = builder.between(eventDate, rangeStart, rangeEnd);
            }
            predicates.add(predicate);
        }
    }
}