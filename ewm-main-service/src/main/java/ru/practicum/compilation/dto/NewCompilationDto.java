package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@Jacksonized
public class NewCompilationDto {
    private final Set<Long> events;

    @NotBlank
    @NotNull
    @Size(min = 1, max = 50)
    private final String title;

    @Builder.Default
    private final Boolean pinned = false;
}
