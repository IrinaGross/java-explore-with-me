package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.ConflictException;

import java.util.List;

@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @NotNull
    public Category createCategory(@NotNull Category category) {
        return categoryRepository.create(category);
    }

    @Override
    public void deleteCategory(@NotNull Long categoryId) {
        var category = categoryRepository.getById(categoryId);
        if (!category.getEvents().isEmpty()) {
            throw new ConflictException(String.format("Для категории с идентификатором %1$s есть связанные события", categoryId));
        }
        categoryRepository.delete(category.getId());
    }

    @Override
    @NotNull
    public Category updateCategory(@NotNull Long categoryId, @NotNull Category category) {
        var current = categoryRepository.getById(categoryId);
        var updated = current.toBuilder().name(category.getName()).build();
        return categoryRepository.update(updated);
    }

    @Override
    @NotNull
    public List<Category> getCategories(@NotNull Pageable pageable) {
        return categoryRepository.getAll(pageable);
    }

    @Override
    @NotNull
    public Category getCategory(@NotNull Long categoryId) {
        return categoryRepository.getById(categoryId);
    }
}
