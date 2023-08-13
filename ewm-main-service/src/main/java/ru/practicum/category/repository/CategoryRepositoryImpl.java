package ru.practicum.category.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.Category;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Repository
interface CategoryRepositoryImpl extends CategoryRepository, JpaRepository<Category, Long> {
    @Override
    @NotNull
    default Category create(@NotNull Category category) {
        try {
            return save(category);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(String.format("Категория с наименованием %1$s уже существует", category.getName()));
        }
    }

    @Override
    default void delete(@NotNull Long categoryId) {
        deleteById(categoryId);
    }

    @Override
    @NotNull
    default Category update(@NotNull Category category) {
        try {
            return save(category);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(String.format("Категория с наименованием %1$s уже существует", category.getName()));
        }
    }

    @Override
    @NotNull
    default List<Category> getAll(@NotNull Pageable pageable) {
        return findAll(pageable).toList();
    }

    @Override
    @NotNull
    default Category getById(@NotNull Long categoryId) {
        return findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Категория с идентификатором %1$s не найдена", categoryId)));
    }
}
