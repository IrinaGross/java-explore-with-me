package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.comment.model.CommentStatus;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class CommentDto {
    private final Long id;
    private final String text;
    private final LocalDateTime createdOn;
    private final LocalDateTime publishedOn;
    private final CommentStatus status;
    private final UserShortDto author;
    private final EventShortDto event;
}