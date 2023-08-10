package ru.practicum.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;

import java.util.Objects;

@Mapper(componentModel = "spring", imports = {Objects.class})
public interface CategoryMapper {
    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", expression = "java(Objects.requireNonNull(dto.getName()))")
    Category map(@Nullable NewCategoryDto dto);

    @Nullable
    @Mapping(target = "id", expression = "java(Objects.requireNonNull(model.getId()))")
    @Mapping(target = "name", expression = "java(Objects.requireNonNull(model.getName()))")
    CategoryDto map(@Nullable Category model);
}
