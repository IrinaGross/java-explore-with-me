package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@Jacksonized
public class UpdateCompilationRequest {
    private final Set<Long> events;

    @Size(min = 1, max = 50)
    private final String title;

    private final Boolean pinned;
}
