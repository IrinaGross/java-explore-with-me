package ru.practicum.request.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class EventRequestStatusUpdateResult {
    private final List<ParticipationRequestDto> confirmedRequests;
    private final List<ParticipationRequestDto> rejectedRequests;
}
