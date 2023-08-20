package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.CommentStatus;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.model.EventState;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.BadRequestException;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.Utils.*;

@Service
@RequiredArgsConstructor
class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @NotNull
    public Comment create(@NotNull Long userId, @NotNull Long eventId, @NotNull Comment comment) {
        var event = eventRepository.getEventById(eventId);
        onConflict(() -> event.getState() != EventState.PUBLISHED, () -> "Нельзя комментировать неопубликованное событие");
        var user = userRepository.getById(userId);
        return commentRepository.create(
                comment.toBuilder()
                        .createdAt(LocalDateTime.now())
                        .status(CommentStatus.CREATED)
                        .author(user)
                        .event(event)
                        .build()
        );
    }

    @Override
    @NotNull
    public Comment moderate(@NotNull Long commentId, @NotNull CommentStatus status) {
        check(() -> status == CommentStatus.CREATED, () -> new BadRequestException("Статус CREATED не поддерживается"));
        var comment = commentRepository.getCommentById(commentId);
        onConflict(() -> comment.getStatus() != CommentStatus.CREATED, () -> "Нельзя модерировать опубликованное/отклоненное событие");
        return commentRepository.update(
                comment.toBuilder()
                        .status(status)
                        .build()
        );
    }

    @Override
    @NotNull
    public Comment markAsDeleted(@NotNull Long userId, @NotNull Long commentId) {
        userRepository.getById(userId);
        var comment = commentRepository.getCommentById(commentId);
        onNotFound(() -> Objects.equals(comment.getAuthor().getId(), userId), () -> String.format("Комментарий с идентификатором %1$s не принадлежит автору с идентификатором %2$s", commentId, userId));
        return commentRepository.update(
                comment.toBuilder()
                        .status(CommentStatus.DELETED)
                        .build()
        );
    }

    @Override
    @NotNull
    public Comment updateComment(@NotNull Long userId, @NotNull Long commentId, @NotNull Comment comment) {
        userRepository.getById(userId);
        var current = commentRepository.getCommentById(commentId);
        onNotFound(() -> Objects.equals(current.getAuthor().getId(), userId), () -> String.format("Комментарий с идентификатором %1$s не принадлежит автору с идентификатором %2$s", commentId, userId));
        onConflict(() -> current.getStatus() != CommentStatus.CONFIRMED, () -> "Изменять можно только опубликованное событие");
        var newText = comment.getText();
        return commentRepository.update(
                current.toBuilder()
                        .status(CommentStatus.CREATED)
                        .text(newText == null ? current.getText() : newText)
                        .build()
        );
    }

    @Override
    @NotNull
    public List<Comment> getAll(@Nullable CommentStatus status, @NotNull Pageable pageable) {
        if (status != null) {
            return commentRepository.findAllByStatus(status, pageable);
        } else {
            return commentRepository.getAllComments(pageable);
        }
    }

    @Override
    @NotNull
    public List<Comment> getPublishedComments(@NotNull Long userId, @NotNull Pageable pageable) {
        userRepository.getById(userId);
        return commentRepository.findAllByAuthorIdAndStatus(userId, CommentStatus.CONFIRMED, pageable);
    }
}