package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Utils;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.service.CategoryService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.Const.*;

@Validated
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;
    private final CategoryMapper mapper;

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Validated @NonNull NewCategoryDto dto) {
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(service::createCategory)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    @DeleteMapping("/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(name = "catId") @NonNull Long categoryId) {
        service.deleteCategory(categoryId);
    }

    @PatchMapping("/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(
            @PathVariable(name = "catId") @NonNull Long categoryId,
            @RequestBody @Validated @NonNull NewCategoryDto dto
    ) {
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(it -> service.updateCategory(categoryId, it))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    // Public
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(
            @RequestParam(name = FROM_PARAM, required = false, defaultValue = FROM_DEFAULT) @NonNull Integer from,
            @RequestParam(name = SIZE_PARAM, required = false, defaultValue = SIZE_DEFAULT) @NonNull Integer size
    ) {
        return service.getCategories(Utils.newPage(from, size)).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable(name = "catId") @NonNull Long categoryId) {
        return Optional.of(categoryId)
                .map(service::getCategory)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }
}