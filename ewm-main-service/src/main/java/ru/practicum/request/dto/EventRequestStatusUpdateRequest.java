package ru.practicum.request.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.request.model.RequestStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@Jacksonized
public class EventRequestStatusUpdateRequest {
    @NotNull
    @NotEmpty
    private final List<Long> requestIds;
    @NotNull
    private final RequestStatus status;
}
