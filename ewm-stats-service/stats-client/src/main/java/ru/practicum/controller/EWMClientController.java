package ru.practicum.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitRequestDto;
import ru.practicum.service.EWMClientService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.EwmStatsClient.*;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class EWMClientController {
    private final EWMClientService service;
    private static final DateTimeFormatter CLIENT_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/hit")
    public ResponseEntity<?> addStatisticRecord(@RequestBody @NonNull HitRequestDto hitRequestDto) {
        return service.addStatisticRecord(hitRequestDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStatistics(
            @RequestParam(name = START_REQUEST_PARAM) @NonNull String start,
            @RequestParam(name = END_REQUEST_PARAM) @NonNull String end,
            @RequestParam(name = URIS_REQUEST_PARAM, required = false) @Nullable List<String> uris,
            @RequestParam(name = UNIQUE_REQUEST_PARAM, required = false, defaultValue = "false") @NonNull Boolean unique
    ) {
        LocalDateTime.parse(start, CLIENT_DATE_TIME);
        LocalDateTime.parse(end, CLIENT_DATE_TIME);
        return service.getStatistics(start, end, uris, unique);
    }
}
