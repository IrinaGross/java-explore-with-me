package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.springframework.lang.Nullable;
import ru.practicum.HitRequestDto;
import ru.practicum.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {
    @Nullable
    Hit map(@Nullable HitRequestDto hitRequestDto);
}