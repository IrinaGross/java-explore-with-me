package ru.practicum.event.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.SortType;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    @NotNull
    List<Event> searchEvents(
            @Nullable List<Long> users,
            @Nullable List<String> states,
            @Nullable List<Long> categories,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @NotNull Pageable pageable
    );

    @NotNull
    Event updateEvent(
            @NotNull Long userId,
            @NotNull Long eventId,
            @Nullable Long categoryId,
            @NotNull Event event
    );

    @NotNull
    Event updateEvent(
            @NotNull Long eventId,
            @Nullable Long categoryId,
            @NotNull Event event
    );

    @NotNull
    Event getEvent(@NotNull Long eventId);

    @NotNull
    List<Event> getAll(
            @Nullable String query,
            @Nullable List<Long> categories,
            @Nullable Boolean paid,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @Nullable Boolean onlyAvailable,
            @Nullable SortType sort,
            @NotNull Pageable pageable);

    @NotNull
    List<Event> getUserEvents(@NotNull Long userId, @NotNull Pageable pageable);

    @NotNull
    Event addEvent(@NotNull Long userId, @NotNull Long categoryId, @NotNull Event event);

    @NotNull
    Event getEvent(@NotNull Long userId, @NotNull Long eventId);
}