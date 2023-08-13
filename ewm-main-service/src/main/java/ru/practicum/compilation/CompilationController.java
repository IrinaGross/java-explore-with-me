package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Utils;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.service.CompilationService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.Const.*;

@Validated
@RestController
@RequiredArgsConstructor
public class CompilationController {
    private static final String COMP_ID_NAME = "compId";

    private final CompilationService service;
    private final CompilationMapper mapper;

    //Admin
    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @NonNull @Validated NewCompilationDto dto) {
        var events = dto.getEvents();
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(it -> service.createCompilation(it, events))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();

    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable(name = COMP_ID_NAME) @NonNull Long compilationId) {
        service.deleteCompilation(compilationId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(
            @PathVariable(name = COMP_ID_NAME) @NonNull Long compilationId,
            @RequestBody @NonNull @Validated UpdateCompilationRequest dto
    ) {
        var events = dto.getEvents();
        return Optional.of(dto)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .map(it -> service.updateCompilation(compilationId, it, events))
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }
    //End admin

    //Public
    @GetMapping("/compilations")
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getAll(
            @RequestParam(name = "pinned", required = false) @Nullable Boolean pinned,
            @RequestParam(name = FROM_PARAM, required = false, defaultValue = FROM_DEFAULT) @NonNull Integer from,
            @RequestParam(name = SIZE_PARAM, required = false, defaultValue = SIZE_DEFAULT) @NonNull Integer size
    ) {
        return service.getAll(pinned, Utils.newPage(from, size)).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getById(@PathVariable(name = COMP_ID_NAME) @NonNull Long compilationId) {
        return Optional.of(compilationId)
                .map(service::getById)
                .map(it -> Objects.requireNonNull(mapper.map(it)))
                .get();
    }
    //End public
}