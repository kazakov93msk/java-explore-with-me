package ru.practicum.ewmservice.comment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCommentDto {
    @Size(max = 255)
    @NotBlank
    private String annotation;
    @Size(max = 2000)
    @NotBlank
    private String text;
}
