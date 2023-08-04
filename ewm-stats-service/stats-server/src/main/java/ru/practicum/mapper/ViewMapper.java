package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.springframework.lang.Nullable;
import ru.practicum.ViewResponseDto;
import ru.practicum.model.View;

@Mapper(componentModel = "spring")
public interface ViewMapper {
    @Nullable
    ViewResponseDto map(@Nullable View view);
}