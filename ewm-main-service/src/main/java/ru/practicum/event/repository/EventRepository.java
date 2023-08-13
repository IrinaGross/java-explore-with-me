package ru.practicum.event.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import ru.practicum.event.model.Event;

import java.util.List;
import java.util.Set;

public interface EventRepository {
    @NotNull
    Event add(@NotNull Event event);

    @NotNull
    Event getEventById(@NotNull Long eventId);

    @NotNull
    Event update(@NotNull Event updated);

    @NotNull
    List<Event> findAllByIdIn(@NotNull Set<Long> ids);

    @NotNull
    List<Event> findAllByInitiatorId(@NotNull Long userId, @NotNull Pageable pageable);

//    @NotNull
//    List<Event> searchEvents(
//            @Nullable List<Long> users,
//            @Nullable List<EventState> states,
//            @Nullable List<Long> categories,
//            @Nullable LocalDateTime rangeStart,
//            @Nullable LocalDateTime rangeEnd,
//            @NotNull Pageable pageable
//    );
//
//    @NotNull
//    List<Event> getEvents(
//            @Nullable String query,
//            @Nullable List<Long> categories,
//            @Nullable Boolean paid,
//            @Nullable LocalDateTime rangeStart,
//            @Nullable LocalDateTime rangeEnd,
//            @Nullable Boolean onlyAvailable,
//            @Nullable SortType sort,
//            @NotNull Pageable pageable
//    );
}