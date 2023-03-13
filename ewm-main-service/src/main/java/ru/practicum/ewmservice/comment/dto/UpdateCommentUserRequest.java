package ru.practicum.ewmservice.comment.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentUserRequest {
    @Size(max = 255)
    private String annotation;
    @Size(max = 2000)
    private String text;

    @Override
    public String toString() {
        return "UpdateCommentUserRequest{" +
                "annotation='" + annotation + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
