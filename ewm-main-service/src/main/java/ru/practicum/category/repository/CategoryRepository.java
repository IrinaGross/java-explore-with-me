package ru.practicum.category.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.practicum.category.model.Category;

import java.util.List;

public interface CategoryRepository {
    @NonNull
    Category create(@NonNull Category category);

    void delete(@NonNull Long categoryId);

    @NonNull
    List<Category> getAll(@NotNull Pageable pageable);

    @NonNull
    Category getById(@NotNull Long categoryId);

    @NonNull
    Category update(@NotNull Category category);
}
