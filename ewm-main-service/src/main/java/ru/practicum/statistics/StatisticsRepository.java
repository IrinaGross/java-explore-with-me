package ru.practicum.statistics;

import org.jetbrains.annotations.NotNull;
import ru.practicum.event.model.Event;

public interface StatisticsRepository {
    void addStatisticRecord(@NotNull String requestURI, @NotNull String remoteAddress);

    @NotNull
    Long getViewCount(@NotNull Event event);
}
