package ru.practicum.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.Objects;

@Mapper(componentModel = "spring", imports = {Objects.class})
public interface UserMapper {
    @Nullable
    @Mapping(target = "id", expression = "java(Objects.requireNonNull(model.getId()))")
    @Mapping(target = "email", expression = "java(Objects.requireNonNull(model.getEmail()))")
    @Mapping(target = "name", expression = "java(Objects.requireNonNull(model.getName()))")
    UserDto map(@Nullable User model);

    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(Objects.requireNonNull(dto.getEmail()))")
    @Mapping(target = "name", expression = "java(Objects.requireNonNull(dto.getName()))")
    User map(@Nullable NewUserRequest dto);
}