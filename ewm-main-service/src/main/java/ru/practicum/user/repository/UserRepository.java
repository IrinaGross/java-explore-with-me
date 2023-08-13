package ru.practicum.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.practicum.user.model.User;

import java.util.List;

public interface UserRepository {

    @NonNull
    List<User> findAll(@NonNull Pageable pageable);

    @NonNull
    List<User> findAllByIdIn(@NonNull List<Long> ids, @NonNull Pageable pageable);

    @NonNull
    User createUser(@NonNull User user);

    @NonNull
    User getById(@NonNull Long userId);

    void deleteUser(@NonNull Long userId);
}
