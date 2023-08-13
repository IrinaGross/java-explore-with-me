package ru.practicum.event.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.SortType;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomEventRepository {

    @NotNull
    List<Event> searchEvents(
            @Nullable List<Long> users,
            @Nullable List<EventState> states,
            @Nullable List<Long> categories,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @NotNull Pageable pageable
    );

    @NotNull
    List<Event> getEvents(
            @Nullable String query,
            @Nullable List<Long> categories,
            @Nullable Boolean paid,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @Nullable Boolean onlyAvailable,
            @Nullable SortType sort,
            @NotNull Pageable pageable
    );
}
