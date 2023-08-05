package ru.practicum.repository;

import org.springframework.lang.NonNull;
import ru.practicum.model.Hit;
import ru.practicum.model.View;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository {
    void addStatisticRecord(@NonNull Hit hit);

    @NonNull
    List<View> getUrisWithUniqueIP(@NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull List<String> uris);

    @NonNull
    List<View> getUrisStats(@NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull List<String> uris);

    @NonNull
    List<View> getAllUrisWithUniqueIP(@NonNull LocalDateTime start, @NonNull LocalDateTime end);

    @NonNull
    List<View> getAllUrisStats(@NonNull LocalDateTime start, @NonNull LocalDateTime end);
}
