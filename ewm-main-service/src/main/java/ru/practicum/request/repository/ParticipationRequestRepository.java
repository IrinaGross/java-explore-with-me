package ru.practicum.request.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.practicum.request.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository {
    @NotNull
    ParticipationRequest create(@NotNull ParticipationRequest request);

    @Nullable
    ParticipationRequest findRequestBy(@NotNull Long userId, @NotNull Long eventId);

    @NotNull
    ParticipationRequest getRequestById(@NotNull Long requestId);

    @NotNull
    ParticipationRequest update(@NotNull ParticipationRequest request);

    @NotNull
    List<ParticipationRequest> findAllByUserId(@NotNull Long userId);

    @NotNull
    List<ParticipationRequest> findAllByEventId(@NotNull Long eventId);
}
