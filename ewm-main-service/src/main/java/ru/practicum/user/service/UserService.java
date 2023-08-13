package ru.practicum.user.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import ru.practicum.user.model.User;

import java.util.List;

public interface UserService {
    @NotNull
    List<User> getAllUsers(@Nullable List<Long> ids, @NotNull Pageable pageable);

    @NotNull
    User createUser(@NotNull User user);

    void deleteUser(@NotNull Long userId);
}
