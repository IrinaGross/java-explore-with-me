package ru.practicum.comment.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.CommentStatus;

import java.util.List;

public interface CommentService {
    @NotNull
    Comment create(@NotNull Long userId, @NotNull Long eventId, @NotNull Comment comment);

    @NotNull
    Comment moderate(@NotNull Long commentId, @NotNull CommentStatus status);

    @NotNull
    Comment markAsDeleted(@NotNull Long userId, @NotNull Long commentId);

    @NotNull
    Comment updateComment(@NotNull Long userId, @NotNull Long commentId, @NotNull Comment comment);

    @NotNull
    List<Comment> getAll(@Nullable CommentStatus status, @NotNull Pageable pageable);

    @NotNull
    List<Comment> getPublishedComments(@NotNull Long userId, @NotNull Pageable pageable);
}