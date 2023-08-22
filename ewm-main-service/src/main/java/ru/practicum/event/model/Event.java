package ru.practicum.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.category.model.Category;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.CommentStatus;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @Column(name = "event_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_title", length = 120, nullable = false)
    private String title;

    @Column(name = "event_annotation", length = 2000, nullable = false)
    private String annotation;

    @Column(name = "event_description", length = 7000, nullable = false)
    private String description;

    @Column(name = "event_created_date", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "event_latitude", nullable = false)
    private Float lat;

    @Column(name = "event_longitude", nullable = false)
    private Float lon;

    @Column(name = "event_limit", nullable = false)
    private Integer limit;

    @Column(name = "event_paid", nullable = false)
    private Boolean paid;

    @Column(name = "event_need_moderation_requests", nullable = false)
    private Boolean needModerationRequests;

    @Column(name = "event_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(name = "event_published_date")
    private LocalDateTime publishedOn;

    @Column(name = "event_view_count")
    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "event_category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "event_initiator_id", nullable = false)
    private User initiator;

    @OneToMany(mappedBy = "event")
    private Collection<ParticipationRequest> requests;

    @ManyToMany(mappedBy = "events")
    private Collection<Compilation> compilations;

    @ManyToMany(mappedBy = "event")
    private Collection<Comment> comments;

    public Collection<ParticipationRequest> getConfirmedRequests() {
        return requests.stream()
                .filter(it -> it.getStatus() == RequestStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    public Collection<Comment> getConfirmedComments() {
        return comments.stream()
                .filter(it -> it.getStatus() == CommentStatus.CONFIRMED)
                .collect(Collectors.toList());
    }
}