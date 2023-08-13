package ru.practicum.category.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import ru.practicum.category.model.Category;

import java.util.List;

public interface CategoryRepository {
    @NotNull
    Category create(@NotNull Category category);

    void delete(@NotNull Long categoryId);

    @NotNull
    List<Category> getAll(@NotNull Pageable pageable);

    @NotNull
    Category getCategoryById(@NotNull Long categoryId);

    @NotNull
    Category update(@NotNull Category category);
}
