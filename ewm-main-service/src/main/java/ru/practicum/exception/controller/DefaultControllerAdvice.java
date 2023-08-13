package ru.practicum.exception.controller;

import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class DefaultControllerAdvice {
    private static final String MESSAGE_KEY = "error";
    private static final String REASON_KEY = "reason";
    private static final String STATUS_KEY = "status";
    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String BAD_REQUEST_STATUS = "BAD_REQUEST";
    private static final String CONFLICT_STATUS = "CONFLICT";
    private static final String NOT_FOUND_STATUS = "NOT_FOUND";
    private static final String CONFLICT_REASON = "Integrity constraint has been violated.";
    private static final String BAD_REQUEST_REASON = "Incorrectly made request.";
    private static final String NOT_FOUND_REASON = "The required object was not found.";

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(getCustomBody(ex, BAD_REQUEST_STATUS, BAD_REQUEST_REASON));
    }

    @ExceptionHandler({ConflictException.class, DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleConflictException(RuntimeException ex) {
        return new ResponseEntity<>(getCustomBody(ex, CONFLICT_STATUS, CONFLICT_REASON), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(getCustomBody(ex, NOT_FOUND_STATUS, NOT_FOUND_REASON), HttpStatus.NOT_FOUND);
    }

    @NonNull
    private static Map<String, Object> getCustomBody(
            @NonNull RuntimeException e,
            @NonNull String status,
            @NonNull String reason
    ) {
        var message = e.getMessage();
        return Map.of(
                STATUS_KEY, status,
                REASON_KEY, reason,
                MESSAGE_KEY, message != null ? message : "",
                TIMESTAMP_KEY, LocalDateTime.now()
        );
    }
}
