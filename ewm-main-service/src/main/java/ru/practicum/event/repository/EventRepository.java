package ru.practicum.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.SortType;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository {
    @NonNull
    Event add(@NonNull Event event);

    @NonNull
    List<Event> getUserEvents(@NonNull Long userId, @NonNull Pageable pageable);

    @NonNull
    Event getById(@NonNull Long eventId);

    @NonNull
    Event update(@NonNull Event updated);

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
    List<Event> getEvents(
            @Nullable String query,
            @Nullable List<Long> categories,
            @Nullable Boolean paid,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @Nullable Boolean onlyAvailable,
            @Nullable SortType sort,
            @NonNull Pageable pageable
    );
}