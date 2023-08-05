package ru.practicum.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.practicum.model.Hit;
import ru.practicum.model.View;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public void addStatisticRecord(@NotNull Hit hit) {
        statsRepository.addStatisticRecord(hit);
    }

    @Override
    public @NotNull List<View> getStatistics(
            @NotNull LocalDateTime start,
            @NotNull LocalDateTime end,
            @NonNull List<String> uris,
            @NotNull Boolean unique
    ) {
        if (unique && !uris.isEmpty()) {
            return statsRepository.getUrisWithUniqueIP(start, end, uris);
        } else if (!uris.isEmpty()) {
            return statsRepository.getUrisStats(start, end, uris);
        } else if (unique) {
            return statsRepository.getAllUrisWithUniqueIP(start, end);
        } else {
            return statsRepository.getAllUrisStats(start, end);
        }
    }
}
