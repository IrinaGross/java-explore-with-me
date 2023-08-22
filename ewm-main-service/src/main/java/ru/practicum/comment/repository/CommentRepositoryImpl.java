package ru.practicum.comment.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.comment.model.Comment;
import ru.practicum.exception.NotFoundException;

@Repository
interface CommentRepositoryImpl extends CommentRepository, JpaRepository<Comment, Long> {
    @Override
    @NotNull
    default Comment create(@NotNull Comment model) {
        return save(model);
    }

    @Override
    @NotNull
    default Comment getCommentById(@NotNull Long commentId) {
        return findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Комментарий с идентификатором %1$s не найден", commentId)));
    }

    @Override
    @NotNull
    default Comment update(@NotNull Comment model) {
        return save(model);
    }
}