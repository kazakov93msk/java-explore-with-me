package ru.practicum.ewmservice.comment.dto;

import lombok.*;
import ru.practicum.ewmservice.comment.property.CommentStatus;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentAdminRequest {
    @Size(max = 255)
    private String annotation;
    @Size(max = 2000)
    private String text;
    private CommentStatus status;

    @Override
    public String toString() {
        return "UpdateCommentAdminRequest{" +
                "annotation='" + annotation + '\'' +
                ", text='" + text + '\'' +
                ", commentStatus=" + status +
                '}';
    }
}
