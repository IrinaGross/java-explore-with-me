package ru.practicum.statistics;

import org.jetbrains.annotations.NotNull;
import ru.practicum.event.model.Event;

import java.util.List;
import java.util.Map;

public interface StatisticsRepository {
    void addStatisticRecord(@NotNull String requestURI, @NotNull String remoteAddress);

    @NotNull
    Map<Long, Long> getViewCount(@NotNull List<Event> events);
}
