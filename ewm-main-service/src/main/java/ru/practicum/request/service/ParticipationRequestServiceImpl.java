package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.request.repository.ParticipationRequestRepository;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final ParticipationRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    private static final Function<Event, Boolean> canAutoAdd =
            (event) -> event.getLimit() == 0 || !event.getNeedModerationRequests();

    @Override
    @NonNull
    public ParticipationRequest createParticipationRequest(@NonNull Long userId, @NonNull Long eventId) {
        var event = eventRepository.getEventById(eventId);
        checkLimit(event);
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }
        var request = requestRepository.findRequestBy(userId, eventId);
        if (request != null) {
            throw new ConflictException("Нельзя добавить повторный запрос");
        }
        return requestRepository.create(
                ParticipationRequest.builder()
                        .createdAt(LocalDateTime.now())
                        .event(event)
                        .user(userRepository.getById(userId))
                        .status(canAutoAdd.apply(event) ? RequestStatus.CONFIRMED : RequestStatus.PENDING)
                        .build()
        );
    }

    @Override
    @NonNull
    public ParticipationRequest cancelParticipationRequest(@NotNull Long userId, @NotNull Long requestId) {
        userRepository.getById(userId);
        var request = requestRepository.getRequestById(requestId);
        if (request.getStatus() == RequestStatus.CANCELED) {
            return request;
        }
        return requestRepository.update(
                request.toBuilder()
                        .status(RequestStatus.CANCELED)
                        .build()
        );
    }

    @Override
    @NonNull
    public List<ParticipationRequest> getRequestsByUser(@NonNull Long userId) {
        userRepository.getById(userId);
        return requestRepository.getRequestsByUserId(userId);
    }

    @Override
    @NotNull
    public List<ParticipationRequest> updateRequestsStatuses(
            @NotNull Long userId,
            @NotNull Long eventId,
            @NotNull List<Long> requestIds,
            @NotNull RequestStatus status
    ) {
        userRepository.getById(userId);
        var event = eventRepository.getEventById(eventId);
        checkLimit(event);
        checkInitiator(event, userId);
        var requests = new ArrayList<ParticipationRequest>(requestIds.size());
        for (Long requestId : requestIds) {
            var request = requestRepository.getRequestById(requestId);
            if (request.getStatus() != RequestStatus.PENDING) {
                throw new ConflictException("Статус можно изменить только у заявок, находящихся в состоянии ожидания");
            }
            if (canAutoAdd.apply(event)) {
                requests.add(requestRepository.update(request.toBuilder().status(status).build()));
                break;
            }
            ParticipationRequest updated;
            if (event.getConfirmedRequests().size() >= event.getLimit()) {
                updated = request.toBuilder().status(RequestStatus.REJECTED).build();
            } else {
                updated = request.toBuilder().status(status).build();
            }
            requests.add(requestRepository.update(updated));
        }
        return requests;
    }

    @Override
    @NotNull
    public List<ParticipationRequest> getRequestsForEvent(@NotNull Long userId, @NotNull Long eventId) {
        userRepository.getById(userId);
        var event = eventRepository.getEventById(eventId);
        checkInitiator(event, userId);
        return requestRepository.getRequestsByEventId(eventId);
    }

    private static void checkLimit(Event event) {
        if (event.getLimit() == 0) {
            return;
        }
        if (event.getConfirmedRequests().size() >= event.getLimit()) {
            throw new ConflictException("Достигнут лимит запросов на участие");
        }
    }

    private static void checkInitiator(Event event, Long userId) {
        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflictException(String.format("Событие с идентификатором %1$s не принадлежит пользователю с идентификатором %2$s", event.getId(), userId));
        }
    }
}