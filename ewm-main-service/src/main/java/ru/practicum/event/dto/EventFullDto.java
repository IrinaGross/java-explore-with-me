package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.event.model.EventState;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@Jacksonized
public class EventFullDto {
    private final Long id;
    private final String annotation;
    private final CategoryDto category;
    private final Long confirmedRequests;
    private final LocalDateTime createdOn;
    private final String description;
    private final LocalDateTime eventDate;
    private final UserShortDto initiator;
    private final LocationDto location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final LocalDateTime publishedOn;
    private final Boolean requestModeration;
    private final EventState state;
    private final String title;
    private final Collection<CommentDto> comments;
    private final Long views;
}