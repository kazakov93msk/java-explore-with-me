package ru.practicum.ewmservice.comment.model;

import lombok.*;
import ru.practicum.ewmservice.comment.property.CommentStatus;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 255)
    private String annotation;
    @Size(max = 2000)
    private String text;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "commentator_id")
    private User commentator;
    private LocalDateTime created;
    private CommentStatus status;
}
