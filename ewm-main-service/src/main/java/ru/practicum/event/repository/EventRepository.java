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
}