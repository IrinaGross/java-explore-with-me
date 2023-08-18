package ru.practicum.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.RequestStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ParticipationRequestMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "created", source = "createdAt")
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "user.id")
    @Mapping(target = "status", source = "status")
    ParticipationRequestDto map(ParticipationRequest model);

    default EventRequestStatusUpdateResult map(List<ParticipationRequest> requests) {
        var groups = requests.stream()
                .map(this::map)
                .collect(Collectors.groupingBy(ParticipationRequestDto::getStatus));
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(groups.getOrDefault(RequestStatus.CONFIRMED, Collections.emptyList()))
                .rejectedRequests(groups.getOrDefault(RequestStatus.REJECTED, Collections.emptyList()))
                .build();
    }
}