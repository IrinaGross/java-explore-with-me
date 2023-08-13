package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.statistics.StatisticsRepository;
import ru.practicum.user.mapper.UserMapper;

import java.util.Objects;

@Mapper(componentModel = "spring", imports = {Objects.class})
public abstract class EventMapper {
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected CategoryMapper categoryMapper;
    @Autowired
    protected StatisticsRepository statisticsRepository;

    @Nullable
    @Mapping(target = "id", source = "id")
    @Mapping(target = "annotation", source = "annotation")
    @Mapping(target = "category", expression = "java(Objects.requireNonNull(categoryMapper.map(model.getCategory())))")
    @Mapping(target = "confirmedRequests", expression = "java((long)model.getConfirmedRequests().size())")
    @Mapping(target = "createdOn", source = "createdAt")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "initiator", expression = "java(Objects.requireNonNull(userMapper.mapToShort(model.getInitiator())))")
    @Mapping(target = "location", expression = "java(toLocationDto(model.getLat(), model.getLon()))")
    @Mapping(target = "paid", source = "paid")
    @Mapping(target = "participantLimit", source = "limit")
    @Mapping(target = "requestModeration", source = "needModerationRequests")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "publishedOn", source = "publishedOn")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "views", expression = "java(statisticsRepository.getViewCount(model))")
    public abstract EventFullDto mapToFull(@Nullable Event model);

    @Nullable
    @Mapping(target = "id", source = "id")
    @Mapping(target = "annotation", source = "annotation")
    @Mapping(target = "category", expression = "java(Objects.requireNonNull(categoryMapper.map(model.getCategory())))")
    @Mapping(target = "confirmedRequests", expression = "java((long)model.getConfirmedRequests().size())")
    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "initiator", expression = "java(Objects.requireNonNull(userMapper.mapToShort(model.getInitiator())))")
    @Mapping(target = "paid", source = "paid")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "views", expression = "java(statisticsRepository.getViewCount(model))")
    public abstract EventShortDto mapToShort(@Nullable Event model);

    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "annotation", source = "annotation")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "lat", source = "location.lat")
    @Mapping(target = "lon", source = "location.lon")
    @Mapping(target = "limit", source = "participantLimit")
    @Mapping(target = "paid", source = "paid")
    @Mapping(target = "needModerationRequests", source = "requestModeration")
    @Mapping(target = "state", expression = "java(form(dto.getStateAction()))")
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "compilations", ignore = true)
    public abstract Event map(@Nullable UpdateEventAdminRequest dto);

    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "annotation", source = "annotation")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "lat", source = "location.lat")
    @Mapping(target = "lon", source = "location.lon")
    @Mapping(target = "limit", source = "participantLimit")
    @Mapping(target = "paid", source = "paid")
    @Mapping(target = "needModerationRequests", source = "requestModeration")
    @Mapping(target = "state", expression = "java(form(dto.getStateAction()))")
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "compilations", ignore = true)
    public abstract Event map(@Nullable UpdateEventUserRequest dto);

    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "annotation", source = "annotation")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "lat", source = "location.lat")
    @Mapping(target = "lon", source = "location.lon")
    @Mapping(target = "limit", source = "participantLimit")
    @Mapping(target = "paid", source = "paid")
    @Mapping(target = "needModerationRequests", source = "requestModeration")
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "compilations", ignore = true)
    public abstract Event map(@Nullable NewEventDto dto);

    protected LocationDto toLocationDto(Float lat, Float lon) {
        return LocationDto.builder()
                .lat(lat)
                .lon(lon)
                .build();
    }

    @Nullable
    protected EventState form(@Nullable AdminStateAction action) {
        if (action == null) {
            return null;
        }
        switch (action) {
            case PUBLISH_EVENT:
                return EventState.PUBLISHED;
            case REJECT_EVENT:
                return EventState.CANCELED;
            default:
                throw new RuntimeException("Another action is not supported yet");
        }
    }

    @Nullable
    protected EventState form(@Nullable UserStateAction action) {
        if (action == null) {
            return null;
        }
        switch (action) {
            case SEND_TO_REVIEW:
                return EventState.PENDING;
            case CANCEL_REVIEW:
                return EventState.CANCELED;
            default:
                throw new RuntimeException("Another action is not supported yet");
        }
    }
}