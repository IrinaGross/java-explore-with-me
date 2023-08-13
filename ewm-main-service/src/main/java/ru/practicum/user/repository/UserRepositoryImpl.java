package ru.practicum.user.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.model.User;

@Repository
interface UserRepositoryImpl extends UserRepository, CrudRepository<User, Long> {

    @Override
    @NotNull
    default User createUser(@NotNull User user) {
        try {
            return save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(String.format("Пользователь с email %1$s уже существует", user.getEmail()));
        }
    }

    @Override
    @NotNull
    default User getById(@NotNull Long userId) {
        return findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с идентефикатором %1$s не найден", userId)));
    }

    @Override
    default void deleteUser(@NotNull Long userId) {
        deleteById(userId);
    }
}
