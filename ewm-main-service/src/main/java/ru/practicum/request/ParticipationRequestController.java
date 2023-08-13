package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.ParticipationRequestMapper;
import ru.practicum.request.service.ParticipationRequestService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.Const.EVENT_ID_VAR_NAME;
import static ru.practicum.Const.USER_ID_VAR_NAME;

@Validated
@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
public class ParticipationRequestController {
    private final ParticipationRequestService service;
    private final ParticipationRequestMapper mapper;

    @PostMapping("/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @RequestParam(name = EVENT_ID_VAR_NAME) @NonNull Long eventId
    ) {
        return Optional.of(service.createParticipationRequest(userId, eventId))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    @PatchMapping("/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancel(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @PathVariable(name = "requestId") @NonNull Long requestId
    ) {
        return Optional.of(service.cancelParticipationRequest(userId, requestId))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    @GetMapping("/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestsByUser(@PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId) {
        return service.getRequestsByUser(userId).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @PatchMapping("/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateRequestsStatuses(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @PathVariable(name = EVENT_ID_VAR_NAME) @NonNull Long eventId,
            @RequestBody @Validated @NonNull EventRequestStatusUpdateRequest dto
    ) {
        return Optional.of(service.updateRequestsStatuses(userId, eventId, dto.getRequestIds(), dto.getStatus()))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    @GetMapping("/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestsForEvent(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @PathVariable(name = EVENT_ID_VAR_NAME) @NonNull Long eventId
    ) {
        return service.getRequestsForEvent(userId, eventId).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }
}