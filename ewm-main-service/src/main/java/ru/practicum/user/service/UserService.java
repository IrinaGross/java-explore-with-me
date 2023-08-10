package ru.practicum.user.service;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.practicum.user.model.User;

import java.util.List;

public interface UserService {
    @NonNull
    List<User> getAllUsers(@Nullable List<Long> ids, @NonNull Pageable pageable);

    @NonNull
    User createUser(@NonNull User user);

    void deleteUser(@NonNull Long userId);
}
