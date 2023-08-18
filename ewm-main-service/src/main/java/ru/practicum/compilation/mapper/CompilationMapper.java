package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CompilationMapper {
    @Autowired
    private EventMapper eventMapper;

    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "pinned", source = "pinned")
    @Mapping(target = "events", ignore = true)
    public abstract Compilation map(@Nullable NewCompilationDto dto);

    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "pinned", source = "pinned")
    @Mapping(target = "events", ignore = true)
    public abstract Compilation map(@Nullable UpdateCompilationRequest dto);

    @Nullable
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "pinned", source = "pinned")
    @Mapping(target = "events", expression = "java(mapEvents(model.getEvents()))")
    public abstract CompilationDto map(@Nullable Compilation model);

    protected List<EventShortDto> mapEvents(Collection<Event> events) {
        return events.stream()
                .map(eventMapper::mapToShort)
                .collect(Collectors.toList());
    }
}
