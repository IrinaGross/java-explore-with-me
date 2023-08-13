package ru.practicum.request.service;

import org.jetbrains.annotations.NotNull;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.RequestStatus;

import java.util.List;

public interface ParticipationRequestService {
    @NotNull
    ParticipationRequest createParticipationRequest(@NotNull Long userId, @NotNull Long eventId);

    @NotNull
    ParticipationRequest cancelParticipationRequest(@NotNull Long userId, @NotNull Long requestId);

    @NotNull
    List<ParticipationRequest> getRequestsByUser(@NotNull Long userId);

    @NotNull
    List<ParticipationRequest> updateRequestsStatuses(
            @NotNull Long userId,
            @NotNull Long eventId,
            @NotNull List<Long> requestIds,
            @NotNull RequestStatus status
    );

    @NotNull
    List<ParticipationRequest> getRequestsForEvent(@NotNull Long userId, @NotNull Long eventId);
}
