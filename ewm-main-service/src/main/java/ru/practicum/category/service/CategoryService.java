package ru.practicum.category.service;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.practicum.category.model.Category;

import java.util.List;

public interface CategoryService {
    @NonNull
    Category createCategory(@NonNull Category category);

    void deleteCategory(@NonNull Long categoryId);

    @NonNull
    Category updateCategory(@NonNull Long categoryId, @NonNull Category category);

    @NonNull
    List<Category> getCategories(@NonNull Pageable pageable);

    @NonNull
    Category getCategory(@NonNull Long categoryId);
}
