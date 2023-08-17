package ru.practicum.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.practicum.HitRequestDto;
import ru.practicum.event.model.Event;
import ru.practicum.service.EWMClientService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.Const.DATE_PATTERN;

@Repository
@RequiredArgsConstructor
@Slf4j
class StatisticsRepositoryImpl implements StatisticsRepository {
    private static final String APP_NAME = "ewm-main-service";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private final EWMClientService clientService;

    @Override
    public void addStatisticRecord(@NotNull String requestURI, @NotNull String remoteAddress) {
        try {
            clientService.addStatisticRecord(
                    HitRequestDto.builder()
                            .app(APP_NAME)
                            .uri(requestURI)
                            .ip(remoteAddress)
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to call stats service", e);
        }
    }

    @Override
    @NotNull
    public Map<Long, Long> getViewCount(@NotNull List<Event> events) {
        if (events.isEmpty()) {
            return Collections.emptyMap();
        }
        var urls = new ArrayList<String>();
        var startTime = events.get(0).getCreatedAt();
        var endTime = events.get(0).getCreatedAt();
        for (int i = 0; i < events.size(); i++) {
            var event = events.get(i);
            urls.add("/events/" + event.getId());
            if (i == 0) {
                continue;
            }
            if (event.getCreatedAt().isBefore(startTime)) {
                startTime = event.getCreatedAt();
            }
            if (event.getCreatedAt().isAfter(endTime)) {
                endTime = event.getCreatedAt();
            }
        }
        try {
            var response = clientService.getStatistics(startTime.format(FORMATTER), endTime.format(FORMATTER), urls, true);
            //noinspection unchecked
            var body = Objects.requireNonNull((List<Map<String, Object>>) response.getBody());
            return body.stream()
                    .map(it -> Map.entry(Long.valueOf(((String) it.get("uri")).split("/")[2]), ((Number) it.get("hits")).longValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (Exception e) {
            log.error("Failed to call stats service", e);
            return Collections.emptyMap();
        }
    }
}