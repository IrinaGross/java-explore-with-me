package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @NotNull
    public List<User> getAllUsers(@Nullable List<Long> ids, @NotNull Pageable pageable) {
        if (ids == null) {
            return userRepository.findAll(pageable);
        } else {
            return userRepository.findAllByIdIn(ids, pageable);
        }
    }

    @Override
    @NotNull
    public User createUser(@NotNull User user) {
        return userRepository.createUser(user);
    }

    @Override
    public void deleteUser(@NotNull Long userId) {
        userRepository.deleteUser(userId);
    }
}
