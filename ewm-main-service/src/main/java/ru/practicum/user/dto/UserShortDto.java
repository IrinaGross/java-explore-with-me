package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Jacksonized
public class UserShortDto {
    @NotNull
    private final Long id;

    @NotBlank
    @NotNull
    private final String name;
}
