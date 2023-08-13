package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UserShortDto {
    private final Long id;
    private final String name;
}
