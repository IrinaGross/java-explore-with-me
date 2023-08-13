package ru.practicum.request.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class ParticipationRequest {
    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_created_date", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "request_event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "request_user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    private RequestStatus status;
}
