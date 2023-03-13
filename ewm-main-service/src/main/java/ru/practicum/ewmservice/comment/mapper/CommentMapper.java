package ru.practicum.ewmservice.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.dto.NewCommentDto;
import ru.practicum.ewmservice.comment.dto.UpdateCommentAdminRequest;
import ru.practicum.ewmservice.comment.dto.UpdateCommentUserRequest;
import ru.practicum.ewmservice.comment.model.Comment;
import ru.practicum.ewmservice.comment.property.CommentStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommentMapper {

    public static Comment mapToEntity(NewCommentDto commentDto) {
        if (commentDto == null) {
            return null;
        }
        return Comment.builder()
                .annotation(commentDto.getAnnotation())
                .text(commentDto.getText())
                .created(LocalDateTime.now())
                .status(CommentStatus.PENDING)
                .build();
    }

    public static Comment mapToEntity(UpdateCommentUserRequest commentDto) {
        if (commentDto == null) {
            return null;
        }
        return Comment.builder()
                .annotation(commentDto.getAnnotation())
                .text(commentDto.getText())
                .build();
    }

    public static Comment mapToEntity(UpdateCommentAdminRequest commentDto) {
        if (commentDto == null) {
            return null;
        }
        return Comment.builder()
                .annotation(commentDto.getAnnotation())
                .text(commentDto.getText())
                .status(commentDto.getStatus())
                .build();
    }

    public static CommentDto mapToDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        return CommentDto.builder()
                .id(comment.getId())
                .annotation(comment.getAnnotation())
                .text(comment.getText())
                .created(comment.getCreated())
                .commentatorId(comment.getCommentator().getId())
                .eventId(comment.getEvent().getId())
                .status(comment.getStatus())
                .modified(comment.getModified())
                .build();
    }

    public static List<CommentDto> mapToDto(List<Comment> comments) {
        if (comments == null) {
            return Collections.emptyList();
        }
        return comments.stream().map(CommentMapper::mapToDto).collect(Collectors.toList());
    }


}
