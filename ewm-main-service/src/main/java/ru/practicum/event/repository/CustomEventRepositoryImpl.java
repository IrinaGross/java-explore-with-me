package ru.practicum.event.repository;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.SortType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        var builder = entityManager.getCriteriaBuilder();
        var criteriaQuery = builder.createQuery(Event.class);
        var root = criteriaQuery.from(Event.class);
        var predicates = new ArrayList<Predicate>();
        if (users != null) {
            predicates.add(root.<Long>get("initiator").in(users));
        }
        if (states != null) {
            predicates.add(root.<EventState>get("state").in(states));
        }
        if (categories != null) {
            predicates.add(root.<Long>get("category").in(categories));
        }
        if (rangeStart != null || rangeEnd != null) {
            var eventDate = root.<LocalDateTime>get("eventDate");
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
        var predicateArray = new Predicate[predicates.size()];
        predicateArray = predicates.toArray(predicateArray);
        criteriaQuery.where(builder.and(predicateArray));
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
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
        //TODO реализовать
        return Collections.emptyList();
    }
}