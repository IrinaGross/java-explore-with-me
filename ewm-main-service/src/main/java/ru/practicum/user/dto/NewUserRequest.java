package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@Jacksonized
public class NewUserRequest {
    @NotBlank
    @NotNull
    @Size(min = 2, max = 250)
    private final String name;

    @Email
    @NotNull
    @NotBlank
    @Size(min = 6, max = 254)
    private final String email;
}