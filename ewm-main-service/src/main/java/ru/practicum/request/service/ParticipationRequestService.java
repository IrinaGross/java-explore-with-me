package ru.practicum.request.service;

import org.springframework.lang.NonNull;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.RequestStatus;

import java.util.List;

public interface ParticipationRequestService {
    @NonNull
    ParticipationRequest createParticipationRequest(@NonNull Long userId, @NonNull Long eventId);

    @NonNull
    ParticipationRequest cancelParticipationRequest(@NonNull Long userId, @NonNull Long requestId);

    @NonNull
    List<ParticipationRequest> getRequestsByUser(@NonNull Long userId);

    @NonNull
    List<ParticipationRequest> updateRequestsStatuses(
            @NonNull Long userId,
            @NonNull Long eventId,
            @NonNull List<Long> requestIds,
            @NonNull RequestStatus status
    );

    @NonNull
    List<ParticipationRequest> getRequestsForEvent(@NonNull Long userId, @NonNull Long eventId);
}
