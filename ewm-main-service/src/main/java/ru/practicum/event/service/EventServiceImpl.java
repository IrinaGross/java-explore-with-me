package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.SortType;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @NotNull
    public List<Event> searchEvents(
            @Nullable List<Long> users,
            @Nullable List<String> states,
            @Nullable List<Long> categories,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @NotNull Pageable pageable) {
        return eventRepository.searchEvents(users, states, categories, rangeStart, rangeEnd, pageable);
    }

    @Override
    @NotNull
    public Event updateEvent(
            @NonNull Long userId,
            @NonNull Long eventId,
            @Nullable Long categoryId,
            @NonNull Event event
    ) {
        checkEventDate(event);
        var current = getEvent(userId, eventId);
        switch (current.getState()) {
            case PENDING:
            case CANCELED:
                return updateInternal(categoryId, current, event);
            default:
                throw new ConflictException(String.format("Событие c идентификатором %1$s не доступно к редактированию", eventId));
        }
    }

    @Override
    @NotNull
    public Event updateEvent(
            @NonNull Long eventId,
            @Nullable Long categoryId,
            @NonNull Event event
    ) {
        var current = eventRepository.getEventById(eventId);
        // TODO дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
        var currentState = current.getState();
        var newState = event.getState();
        if (newState == EventState.PUBLISHED && currentState != EventState.PENDING) {
            throw new ConflictException("Событие можно публиковать, только если оно в состоянии ожидания публикации");
        }
        if (newState == EventState.CANCELED && currentState == EventState.PUBLISHED) {
            throw new ConflictException("Событие можно отклонить, только если оно еще не опубликовано");
        }
        return updateInternal(categoryId, current, event);
    }

    @Override
    @NotNull
    public Event getEvent(@NotNull Long eventId) {
        var event = eventRepository.getEventById(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new NotFoundException(String.format("Событие c идентификатором %1$s не опубликовано", eventId));
        }
        return event;
    }

    @Override
    @NotNull
    public List<Event> getAll(
            @Nullable String query,
            @Nullable List<Long> categories,
            @Nullable Boolean paid,
            @Nullable LocalDateTime rangeStart,
            @Nullable LocalDateTime rangeEnd,
            @Nullable Boolean onlyAvailable,
            @Nullable SortType sort,
            @NotNull Pageable newPage
    ) {
        return eventRepository.getEvents(query, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, newPage);
    }

    @Override
    @NotNull
    public List<Event> getUserEvents(@NotNull Long userId, @NotNull Pageable pageable) {
        return eventRepository.findAllByInitiatorId(userId, pageable);
    }

    @Override
    @NotNull
    public Event addEvent(@NotNull Long userId, @NotNull Long categoryId, @NotNull Event event) {
        checkEventDate(event);
        LocalDateTime now = LocalDateTime.now();
        var user = userRepository.getById(userId);
        var category = categoryRepository.getCategoryById(categoryId);
        return eventRepository.add(
                event.toBuilder()
                        .createdAt(now)
                        .category(category)
                        .state(EventState.PENDING)
                        .initiator(user)
                        .requests(Collections.emptyList())
                        .viewCount(0L)
                        .build()
        );
    }

    @Override
    @NotNull
    public Event getEvent(@NotNull Long userId, @NotNull Long eventId) {
        var event = eventRepository.getEventById(eventId);
        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new NotFoundException(String.format("Событие c идентификатором %1$s не найдено для пользователя %2$s", eventId, userId));
        }
        return event;
    }

    private static void checkEventDate(Event event) {
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now().minusHours(2))) {
            throw new ConflictException("Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента");
        }
    }

    @NonNull
    private Event updateInternal(@Nullable Long categoryId, @NonNull Event current, @NonNull Event event) {
        var newTitle = event.getTitle();
        var newAnnotation = event.getAnnotation();
        var newDescription = event.getDescription();
        var newEventData = event.getEventDate();
        var newLat = event.getLat();
        var newLon = event.getLon();
        var newLimit = event.getLimit();
        var newPaid = event.getPaid();
        var newNeedModeration = event.getNeedModerationRequests();
        var newState = event.getState();
        return eventRepository.update(
                current.toBuilder()
                        .title(newTitle == null ? current.getTitle() : newTitle)
                        .annotation(newAnnotation == null ? current.getAnnotation() : newAnnotation)
                        .description(newDescription == null ? current.getDescription() : newDescription)
                        .eventDate(newEventData == null ? current.getEventDate() : newEventData)
                        .lat(newLat == null ? current.getLat() : newLat)
                        .lon(newLon == null ? current.getLon() : newLon)
                        .limit(newLimit == null ? current.getLimit() : newLimit)
                        .paid(newPaid == null ? current.getPaid() : newPaid)
                        .state(newState == null ? current.getState() : newState)
                        .needModerationRequests(newNeedModeration == null ? current.getNeedModerationRequests() : newNeedModeration)
                        .category(categoryId == null ? current.getCategory() : categoryRepository.getCategoryById(categoryId))
                        .publishedOn(newState == EventState.PUBLISHED ? LocalDateTime.now() : current.getPublishedOn())
                        .build()
        );
    }
}