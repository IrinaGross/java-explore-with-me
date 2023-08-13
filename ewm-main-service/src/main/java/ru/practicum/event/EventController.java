package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Utils;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.SortType;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.Const.*;
import static ru.practicum.Utils.checkDates;

@Validated
@RestController
@RequiredArgsConstructor
public class EventController {
    private static final String CATEGORIES_REQUEST_PARAM = "categories";
    private static final String RANGE_START_PARAM = "rangeStart";
    private static final String RANGE_END_PARAM = "rangeEnd";

    private final EventService service;
    private final EventMapper mapper;

    // Admin
    @GetMapping("/admin/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> searchEvents(
            @RequestParam(name = "users", required = false) @Nullable List<Long> users,
            @RequestParam(name = "states", required = false) @Nullable List<EventState> states,
            @RequestParam(name = CATEGORIES_REQUEST_PARAM, required = false) @Nullable List<Long> categories,
            @RequestParam(name = RANGE_START_PARAM, required = false) @DateTimeFormat(pattern = DATE_PATTERN) @Nullable LocalDateTime rangeStart,
            @RequestParam(name = RANGE_END_PARAM, required = false) @DateTimeFormat(pattern = DATE_PATTERN) @Nullable LocalDateTime rangeEnd,
            @RequestParam(name = FROM_PARAM, required = false, defaultValue = FROM_DEFAULT) @NonNull Integer from,
            @RequestParam(name = SIZE_PARAM, required = false, defaultValue = SIZE_DEFAULT) @NonNull Integer size
    ) {
        checkDates(rangeStart, rangeEnd);
        return service.searchEvents(users, states, categories, rangeStart, rangeEnd, Utils.newPage(from, size)).stream()
                .map(mapper::mapToFull)
                .collect(Collectors.toList());
    }

    @PatchMapping("/admin/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(
            @PathVariable(name = EVENT_ID_VAR_NAME) @NonNull Long eventId,
            @RequestBody @Validated @NonNull UpdateEventAdminRequest dto
    ) {
        var categoryId = dto.getCategory();
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(it -> service.updateEvent(eventId, categoryId, it))
                .map(it -> Objects.requireNonNull(mapper.mapToFull(it)))
                .get();
    }
    // End admin

    // Private
    @GetMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getUserEvents(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @RequestParam(name = FROM_PARAM, required = false, defaultValue = FROM_DEFAULT) @NonNull Integer from,
            @RequestParam(name = SIZE_PARAM, required = false, defaultValue = SIZE_DEFAULT) @NonNull Integer size
    ) {
        return service.getUserEvents(userId, Utils.newPage(from, size)).stream()
                .map(mapper::mapToShort)
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @RequestBody @Validated @NonNull NewEventDto dto
    ) {
        var categoryId = dto.getCategory();
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(it -> service.addEvent(userId, categoryId, it))
                .map(it -> Objects.requireNonNull(mapper.mapToFull(it)))
                .get();
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getById(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @PathVariable(name = EVENT_ID_VAR_NAME) @NonNull Long eventId
    ) {
        return Optional.of(service.getEvent(userId, eventId))
                .map(it -> Objects.requireNonNull(mapper.mapToFull(it)))
                .get();
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @PathVariable(name = EVENT_ID_VAR_NAME) @NonNull Long eventId,
            @RequestBody @Validated @NonNull UpdateEventUserRequest dto
    ) {
        var categoryId = dto.getCategory();
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(it -> service.updateEvent(userId, eventId, categoryId, it))
                .map(it -> Objects.requireNonNull(mapper.mapToFull(it)))
                .get();
    }
    // End private

    // Public
    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAll(
            @RequestParam(name = "text", required = false) @Nullable String query,
            @RequestParam(name = CATEGORIES_REQUEST_PARAM, required = false) @Nullable List<Long> categories,
            @RequestParam(name = "paid", required = false) @Nullable Boolean paid,
            @RequestParam(name = RANGE_START_PARAM, required = false) @DateTimeFormat(pattern = DATE_PATTERN) @Nullable LocalDateTime rangeStart,
            @RequestParam(name = RANGE_END_PARAM, required = false) @DateTimeFormat(pattern = DATE_PATTERN) @Nullable LocalDateTime rangeEnd,
            @RequestParam(name = "onlyAvailable", required = false, defaultValue = "false") @NonNull Boolean onlyAvailable,
            @RequestParam(name = "sort", required = false) @Nullable SortType sort,
            @RequestParam(name = FROM_PARAM, required = false, defaultValue = FROM_DEFAULT) @NonNull Integer from,
            @RequestParam(name = SIZE_PARAM, required = false, defaultValue = SIZE_DEFAULT) @NonNull Integer size,
            HttpServletRequest request
    ) {
        checkDates(rangeStart, rangeEnd);
        service.addStatisticsRecord(request.getRequestURI(), request.getRemoteAddr());
        return service.getAll(query, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, Utils.newPage(from, size)).stream()
                .map(mapper::mapToShort)
                .collect(Collectors.toList());
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEvent(@PathVariable(name = "id") @NonNull Long eventId, HttpServletRequest request) {
        service.addStatisticsRecord(request.getRequestURI(), request.getRemoteAddr());
        return Optional.of(eventId)
                .map(service::getEvent)
                .map(it -> Objects.requireNonNull(mapper.mapToFull(it)))
                .get();
    }
    // End public
}