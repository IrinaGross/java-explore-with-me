package ru.practicum.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class View {
    String app;
    String uri;
    Long hits;

    public View(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
