package ru.practicum.event.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.SortType;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
interface EventRepositoryImpl extends EventRepository, JpaRepository<Event, Long> {

    @Override
    @NonNull
    default Event add(@NonNull Event event) {
        try {
            return save(event);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(String.format("Событие с заголовком %1$s уже существует", event.getTitle()));
        }
    }

    @Override
    @NonNull
    default List<Event> getUserEvents(@NonNull Long userId, @NonNull Pageable pageable) {
        return findAllByInitiatorId(userId, pageable);
    }

    @Override
    @NonNull
    default Event getEventById(@NonNull Long eventId) {
        return findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Событие c идентификатором %1$s не найдено", eventId)));
    }

    @Override
    @NonNull
    default Event update(@NonNull Event event) {
        return save(event);
    }

    @Override
    @NotNull
    default List<Event> searchEvents(
            @Nullable List<Long> users,
            @Nullable List<String> states,
            @Nullable List<Long> categories,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @NotNull Pageable pageable
    ) {
        //TODO реализовать
        return Collections.emptyList();
    }

    @Override
    @NonNull
    default List<Event> getEvents(
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

    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);
}