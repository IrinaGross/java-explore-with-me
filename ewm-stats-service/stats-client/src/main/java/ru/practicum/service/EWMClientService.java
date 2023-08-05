package ru.practicum.service;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.practicum.HitRequestDto;

import java.util.List;

public interface EWMClientService {
    @NonNull
    ResponseEntity<?> addStatisticRecord(@NonNull HitRequestDto hitRequestDto);

    @NonNull
    ResponseEntity<?> getStatistics(
            @NonNull String startTime,
            @NonNull String endTime,
            @Nullable List<String> uris,
            @NonNull Boolean unique
    );
}
