package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class UpdateEventAdminRequest {
    @Nullable
    @Size(max = 2000, min = 20)
    private final String annotation;

    @Nullable
    private final Long category;

    @Nullable
    @Size(max = 7000, min = 20)
    private final String description;

    @Nullable
    @FutureOrPresent
    private final LocalDateTime eventDate;

    @Nullable
    private final LocationDto location;

    @Nullable
    private final Boolean paid;

    @Nullable
    private final Long participantLimit;

    @Nullable
    private final Boolean requestModeration;

    @Nullable
    private final AdminStateAction stateAction;

    @Nullable
    @Size(max = 120, min = 3)
    private final String title;
}
