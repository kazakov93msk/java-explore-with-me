package ru.practicum.ewmservice.comment.dto;

import lombok.*;
import ru.practicum.ewmservice.comment.property.CommentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String annotation;
    private String text;
    private Long eventId;
    private Long commentatorId;
    private LocalDateTime created;
    private CommentStatus status;
}