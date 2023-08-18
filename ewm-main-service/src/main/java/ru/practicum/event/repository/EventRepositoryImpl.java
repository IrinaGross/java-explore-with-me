package ru.practicum.event.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

@Repository
interface EventRepositoryImpl extends EventRepository, JpaRepository<Event, Long> {

    @Override
    @NotNull
    default Event add(@NotNull Event event) {
        try {
            return save(event);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(String.format("Событие с заголовком %1$s уже существует", event.getTitle()));
        }
    }

    @Override
    @NotNull
    default Event getEventById(@NotNull Long eventId) {
        return findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Событие c идентификатором %1$s не найдено", eventId)));
    }

    @Override
    @NotNull
    default Event update(@NotNull Event event) {
        return save(event);
    }
}