package ru.practicum.request.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.model.ParticipationRequest;

import java.util.Optional;

@Repository
interface ParticipationRequestRepositoryImpl extends ParticipationRequestRepository, JpaRepository<ParticipationRequest, Long> {

    @Override
    @NotNull
    default ParticipationRequest create(@NotNull ParticipationRequest request) {
        try {
            return save(request);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getLocalizedMessage());
        }
    }

    @Override
    @Nullable
    default ParticipationRequest findRequestBy(@NotNull Long userId, @NotNull Long eventId) {
        return findByUserIdAndEventId(userId, eventId)
                .orElse(null);
    }

    @Override
    @NotNull
    default ParticipationRequest getRequestById(@NotNull Long requestId) {
        return findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Запрос на участие с идентификатором %1$s не найден", requestId)));
    }

    @Override
    @NotNull
    default ParticipationRequest update(@NotNull ParticipationRequest request) {
        return save(request);
    }

    Optional<ParticipationRequest> findByUserIdAndEventId(Long userId, Long eventId);
}