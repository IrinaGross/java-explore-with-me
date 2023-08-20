package ru.practicum.comment.mapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.NewCommentStatusAction;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.CommentStatus;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.exception.BadRequestException;
import ru.practicum.user.mapper.UserMapper;

import java.util.Objects;

@Mapper(componentModel = "spring", imports = {Objects.class})
public abstract class CommentMapper {
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected EventMapper eventMapper;

    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "text")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "event", ignore = true)
    public abstract Comment map(@Nullable NewCommentDto dto);

    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "text")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "event", ignore = true)
    public abstract Comment map(@Nullable UpdateCommentDto dto);

    @Nullable
    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "createdOn", source = "createdAt")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "author", expression = "java(Objects.requireNonNull(userMapper.mapToShort(model.getAuthor())))")
    @Mapping(target = "event", expression = "java(Objects.requireNonNull(eventMapper.mapToShort(model.getEvent())))")
    public abstract CommentDto map(@Nullable Comment model);

    @NotNull
    public CommentStatus mapAction(@NotNull NewCommentStatusAction action) {
        switch (action) {
            case CONFIRM:
                return CommentStatus.CONFIRMED;
            case REJECT:
                return CommentStatus.REJECTED;
            default:
                throw new BadRequestException("other NewCommentStatusAction values is not supported yet");
        }
    }
}
