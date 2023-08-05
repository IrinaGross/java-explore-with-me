package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.HitRequestDto;

import java.util.List;
import java.util.Map;

import static ru.practicum.EwmStatsClient.*;

@Service
@RequiredArgsConstructor
class EWMClientServiceImpl implements EWMClientService {
    private static final String POST_STATS_PATH = "/hit";
    private static final String GET_STATS_PATH = "/stats?start={start}&end={end}&uris={uris}&unique={unique}";

    private final RestTemplate rest;

    @Autowired
    public EWMClientServiceImpl(RestTemplateBuilder builder, @Value("${stats-server.url}") String serverUrl) {
        rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    @NonNull
    @Override
    public ResponseEntity<?> addStatisticRecord(@NonNull HitRequestDto hitRequestDto) {
        return sendRequest(HttpMethod.POST, POST_STATS_PATH, null, hitRequestDto);
    }

    @NonNull
    @Override
    public ResponseEntity<?> getStatistics(
            @NonNull String startTime,
            @NonNull String endTime,
            @Nullable List<String> uris,
            @NonNull Boolean unique
    ) {
        Map<String, Object> params = Map.of(
                START_REQUEST_PARAM, startTime,
                END_REQUEST_PARAM, endTime,
                UNIQUE_REQUEST_PARAM, unique,
                URIS_REQUEST_PARAM, uris == null ? "" : String.join(",", uris)
        );
        return sendRequest(HttpMethod.GET, GET_STATS_PATH, params, null);
    }

    private ResponseEntity<Object> sendRequest(
            @NonNull HttpMethod method,
            @NonNull String path,
            @Nullable Map<String, Object> parameters,
            @Nullable HitRequestDto body
    ) {
        HttpEntity<HitRequestDto> requestEntity = new HttpEntity<>(body, getHeaders());
        ResponseEntity<Object> statsServerResponse;
        try {
            if (parameters != null) {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareResponse(statsServerResponse);
    }

    private static ResponseEntity<Object> prepareResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
