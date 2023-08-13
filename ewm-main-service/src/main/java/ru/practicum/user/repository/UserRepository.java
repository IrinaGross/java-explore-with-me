package ru.practicum.user.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import ru.practicum.user.model.User;

import java.util.List;

public interface UserRepository {

    @NotNull
    List<User> findAll(@NotNull Pageable pageable);

    @NotNull
    List<User> findAllByIdIn(@NotNull List<Long> ids, @NotNull Pageable pageable);

    @NotNull
    User createUser(@NotNull User user);

    @NotNull
    User getById(@NotNull Long userId);

    void deleteUser(@NotNull Long userId);
}
