package ru.practicum.statistics;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
class StatisticsRepositoryImpl implements StatisticsRepository {
    private static final String APP_NAME = "ewm-main-service";

    // private final EWMClientService clientService;
    private final Map<Long, Long> cacheCount = new HashMap<>();

    @Override
    public void addStatisticRecord(@NotNull String requestURI, @NotNull String remoteAddress) {
        //TODO восстановить
//        clientService.addStatisticRecord(
//                HitRequestDto.builder()
//                        .app(APP_NAME)
//                        .uri(requestURI)
//                        .ip(remoteAddress)
//                        .timestamp(LocalDateTime.now())
//                        .build()
//        );
    }

    @Override
    @NotNull
    public Long getViewCount(@NotNull Event event) {
        // clientService.getStatistics(event.getEventDate(), )
        return cacheCount.getOrDefault(event.getId(), 0L);
    }
}
