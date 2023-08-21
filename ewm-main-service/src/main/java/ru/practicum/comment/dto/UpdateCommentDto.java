package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Size;

@Data
@Builder
@Jacksonized
public class UpdateCommentDto {
    @Nullable
    @Size(min = 5, max = 7000)
    private final String text;
}