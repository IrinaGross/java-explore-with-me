package ru.practicum.comment.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.CommentStatus;

import java.util.List;

public interface CommentRepository {
    @NotNull
    Comment create(@NotNull Comment model);

    @NotNull
    Comment getCommentById(@NotNull Long commentId);

    @NotNull
    Comment update(@NotNull Comment model);

    @NotNull
    List<Comment> findAllByStatusOrderByCreatedAtDesc(@NotNull CommentStatus status, @NotNull Pageable pageable);

    @NotNull
    List<Comment> findAllByOrderByCreatedAtDesc(@NotNull Pageable pageable);

    @NotNull
    List<Comment> findAllByAuthorIdAndStatusOrderByCreatedAtDesc(@NotNull Long userId, @NotNull CommentStatus status, @NotNull Pageable pageable);
}