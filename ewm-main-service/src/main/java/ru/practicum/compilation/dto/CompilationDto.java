package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

@Data
@Builder
@Jacksonized
public class CompilationDto {
    private final List<EventShortDto> events;
    private final Long id;
    private final Boolean pinned;
    private final String title;
}
