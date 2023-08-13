package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class NewEventDto {
    @NotNull
    @Size(min = 20, max = 2000)
    private final String annotation;

    @NotNull
    private final Long category;

    @NotNull
    @Size(min = 20, max = 7000)
    private final String description;

    @NotNull
    @FutureOrPresent
    private final LocalDateTime eventDate;

    @NotNull
    private final LocationDto location;

    @NonNull
    @Builder.Default
    private final Boolean paid = false;

    @NonNull
    @Builder.Default
    private final Integer participantLimit = 0;

    @NonNull
    @Builder.Default
    private final Boolean requestModeration = true;

    @NonNull
    @Size(min = 3, max = 120)
    private final String title;
}
