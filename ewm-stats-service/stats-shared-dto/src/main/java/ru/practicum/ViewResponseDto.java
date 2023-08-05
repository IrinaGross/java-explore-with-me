package ru.practicum;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ViewResponseDto {
    private final String app;
    private final String uri;
    private final Long hits;
}
