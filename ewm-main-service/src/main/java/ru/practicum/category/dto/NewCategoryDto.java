package ru.practicum.category.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@Jacksonized
public class NewCategoryDto {

    @NotBlank
    @NotNull
    @Size(min = 1, max = 50)
    private final String name;
}
