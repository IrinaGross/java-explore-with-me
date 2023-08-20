package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@Jacksonized
public class UpdateCommentDto {
    @NotBlank
    @Size(min = 5, max = 7000)
    private final String text;
}