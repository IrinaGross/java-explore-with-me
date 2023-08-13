package ru.practicum.request.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.request.model.RequestStatus;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class ParticipationRequestDto {
    private Long id;
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private RequestStatus status;
}
