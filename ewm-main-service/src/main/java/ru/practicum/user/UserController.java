package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Utils;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.Const.*;

@Validated
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers(
            @RequestParam(name = "ids", required = false) @Nullable List<Long> ids,
            @RequestParam(name = FROM_PARAM, required = false, defaultValue = FROM_DEFAULT) @NonNull Integer from,
            @RequestParam(name = SIZE_PARAM, required = false, defaultValue = SIZE_DEFAULT) @NonNull Integer size,
            HttpServletRequest request
    ) {
        return service.getAllUsers(ids, Utils.newPage(from, size)).stream()
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Validated @NonNull NewUserRequest dto) {
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(service::createUser)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "userId") @NonNull Long userId) {
        service.deleteUser(userId);
    }
}
