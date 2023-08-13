package ru.practicum.request.repository;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.practicum.request.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository {
    @NonNull
    ParticipationRequest create(@NonNull ParticipationRequest request);

    @Nullable
    ParticipationRequest findRequestBy(@NonNull Long userId, @NonNull Long eventId);

    @NonNull
    ParticipationRequest getById(@NonNull Long requestId);

    @NonNull
    ParticipationRequest update(@NonNull ParticipationRequest request);

    @NonNull
    List<ParticipationRequest> getRequestsByUserId(@NonNull Long userId);

    @NonNull
    List<ParticipationRequest> getRequestsByEventId(@NonNull Long eventId);
}
