package ru.practicum.service;

import org.springframework.lang.NonNull;
import ru.practicum.model.Hit;
import ru.practicum.model.View;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void addStatisticRecord(@NonNull Hit hit);

    @NonNull
    List<View> getStatistics(
            @NonNull LocalDateTime start,
            @NonNull LocalDateTime end,
            @NonNull List<String> uris,
            @NonNull Boolean unique
    );
}
