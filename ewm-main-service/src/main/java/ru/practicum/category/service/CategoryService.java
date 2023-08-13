package ru.practicum.category.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import ru.practicum.category.model.Category;

import java.util.List;

public interface CategoryService {
    @NotNull
    Category createCategory(@NotNull Category category);

    void deleteCategory(@NotNull Long categoryId);

    @NotNull
    Category updateCategory(@NotNull Long categoryId, @NotNull Category category);

    @NotNull
    List<Category> getCategories(@NotNull Pageable pageable);

    @NotNull
    Category getCategory(@NotNull Long categoryId);
}
