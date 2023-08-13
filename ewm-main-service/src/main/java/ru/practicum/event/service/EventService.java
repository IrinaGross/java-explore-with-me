package ru.practicum.event.service;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.SortType;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    @NonNull
    List<Event> searchEvents(
            @Nullable List<Long> users,
            @Nullable List<String> states,
            @Nullable List<Long> categories,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @NonNull Pageable pageable
    );

    @NonNull
    Event updateEvent(
            @NonNull Long userId,
            @NonNull Long eventId,
            @Nullable Long categoryId,
            @NonNull Event event
    );

    @NonNull
    Event updateEvent(
            @NonNull Long eventId,
            @Nullable Long categoryId,
            @NonNull Event event
    );

    @NonNull
    Event getEvent(@NonNull Long eventId);

    @NonNull
    List<Event> getAll(
            @Nullable String query,
            @Nullable List<Long> categories,
            @Nullable Boolean paid,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @Nullable Boolean onlyAvailable,
            @Nullable SortType sort,
            @NonNull Pageable pageable);

    @NonNull
    List<Event> getUserEvents(@NonNull Long userId, @NonNull Pageable pageable);

    @NonNull
    Event addEvent(@NonNull Long userId, @NonNull Long categoryId, @NonNull Event event);

    @NonNull
    Event getEvent(@NonNull Long userId, @NonNull Long eventId);
}