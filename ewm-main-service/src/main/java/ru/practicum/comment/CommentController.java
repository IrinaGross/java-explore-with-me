package ru.practicum.comment;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Utils;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.NewCommentStatusAction;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.CommentStatus;
import ru.practicum.comment.service.CommentService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.Const.*;

@Validated
@RestController
@RequiredArgsConstructor
public class CommentController {
    private static final String COMMENT_ID_VAR_NAME = "commId";
    private final CommentService service;
    private final CommentMapper mapper;

    @PostMapping("/admin/comments/{commId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto moderate(
            @PathVariable(name = COMMENT_ID_VAR_NAME) @NonNull Long commentId,
            @RequestParam(name = "status") @NonNull NewCommentStatusAction action
    ) {
        return Optional.of(action)
                .map(it -> Objects.requireNonNull(mapper.mapAction(it)))
                .map(it -> service.moderate(commentId, it))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    @GetMapping("/admin/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAll(
            @RequestParam(name = "status", required = false) @Nullable CommentStatus status,
            @RequestParam(name = FROM_PARAM, required = false, defaultValue = FROM_DEFAULT) @NonNull Integer from,
            @RequestParam(name = SIZE_PARAM, required = false, defaultValue = SIZE_DEFAULT) @NonNull Integer size
    ) {
        return service.getAll(status, Utils.newPage(from, size))
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{userId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @RequestBody @Validated @NonNull NewCommentDto dto
    ) {
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(it -> service.create(userId, dto.getEvent(), it))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    @PostMapping("/users/{userId}/comments/{commId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto markAsDeleted(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @PathVariable(name = COMMENT_ID_VAR_NAME) @NonNull Long commentId
    ) {
        return Optional.of(service.markAsDeleted(userId, commentId))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    @PatchMapping("/users/{userId}/comments/{commId}/update")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto update(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @PathVariable(name = COMMENT_ID_VAR_NAME) @NonNull Long commentId,
            @RequestBody @Validated @NonNull UpdateCommentDto dto
    ) {
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(it -> service.updateComment(userId, commentId, it))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    @GetMapping("/users/{userId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAll(
            @PathVariable(name = USER_ID_VAR_NAME) @NonNull Long userId,
            @RequestParam(name = FROM_PARAM, required = false, defaultValue = FROM_DEFAULT) @NonNull Integer from,
            @RequestParam(name = SIZE_PARAM, required = false, defaultValue = SIZE_DEFAULT) @NonNull Integer size
    ) {
        return service.getPublishedComments(userId, Utils.newPage(from, size))
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }
}