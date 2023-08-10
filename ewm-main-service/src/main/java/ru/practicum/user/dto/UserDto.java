package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UserDto {
    private final Long id;
    private final String name;
    private final String email;
}
