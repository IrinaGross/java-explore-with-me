package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EwmStatsClient {
    public static final String START_REQUEST_PARAM = "start";
    public static final String END_REQUEST_PARAM = "end";
    public static final String URIS_REQUEST_PARAM = "uris";
    public static final String UNIQUE_REQUEST_PARAM = "unique";

    public static void main(String[] args) {
        SpringApplication.run(EwmStatsClient.class, args);
    }
}