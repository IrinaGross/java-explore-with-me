package ru.practicum.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitRequestDto;
import ru.practicum.ViewResponseDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.mapper.ViewMapper;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatsController {
    private static final String STATS_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String START_REQUEST_PARAM = "start";
    private static final String END_REQUEST_PARAM = "end";
    private static final String URIS_REQUEST_PARAM = "uris";
    private static final String UNIQUE_REQUEST_PARAM = "unique";

    private final StatsService service;
    private final HitMapper hitMapper;
    private final ViewMapper viewMapper;

    @PostMapping("/hit")
    public void addStatisticRecord(@RequestBody @NonNull HitRequestDto hitRequestDto) {
        service.addStatisticRecord(Objects.requireNonNull(hitMapper.map(hitRequestDto)));
    }

    @GetMapping("/stats")
    public List<ViewResponseDto> getStatistics(
            @RequestParam(name = START_REQUEST_PARAM) @NonNull @DateTimeFormat(pattern = STATS_DATE_PATTERN) LocalDateTime start,
            @RequestParam(name = END_REQUEST_PARAM) @NonNull @DateTimeFormat(pattern = STATS_DATE_PATTERN) LocalDateTime end,
            @RequestParam(name = URIS_REQUEST_PARAM, required = false) @Nullable List<String> uris,
            @RequestParam(name = UNIQUE_REQUEST_PARAM, required = false, defaultValue = "false") @NonNull Boolean unique
    ) {
        val list = uris == null ? Collections.<String>emptyList() : uris;
        return service.getStatistics(start, end, list, unique)
                .stream()
                .map(viewMapper::map)
                .collect(Collectors.toList());
    }
}
