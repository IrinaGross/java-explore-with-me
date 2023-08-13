package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Data
@Builder
@Jacksonized
public class LocationDto {
    @NotNull
    private final Float lat;
    @NonNull
    private final Float lon;
}
