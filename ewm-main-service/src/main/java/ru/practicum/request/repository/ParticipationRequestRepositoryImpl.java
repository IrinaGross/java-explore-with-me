package ru.practicum.request.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.model.ParticipationRequest;

import java.util.List;
import java.util.Optional;

@Repository
interface ParticipationRequestRepositoryImpl extends ParticipationRequestRepository, JpaRepository<ParticipationRequest, Long> {

    @Override
    @NonNull
    default ParticipationRequest create(@NonNull ParticipationRequest request) {
        try {
            return save(request);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getLocalizedMessage());
        }
    }

    @Override
    @Nullable
    default ParticipationRequest findRequestBy(@NonNull Long userId, @NonNull Long eventId) {
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

    @Override
    @NotNull
    default List<ParticipationRequest> getRequestsByUserId(@NotNull Long userId) {
        return findAllByUserId(userId);
    }

    @Override
    @NotNull
    default List<ParticipationRequest> getRequestsByEventId(@NotNull Long eventId) {
        return findAllByEventId(eventId);
    }

    Optional<ParticipationRequest> findByUserIdAndEventId(Long userId, Long eventId);

    List<ParticipationRequest> findAllByUserId(Long userId);

    List<ParticipationRequest> findAllByEventId(Long eventId);
}