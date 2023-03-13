package ru.practicum.ewmservice.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.dto.UpdateCommentAdminRequest;
import ru.practicum.ewmservice.comment.mapper.CommentMapper;
import ru.practicum.ewmservice.comment.model.Comment;
import ru.practicum.ewmservice.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminCommentController {
    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> findAllPending() {
        log.info("GET: Get all comments with stats = PENDING");
        return CommentMapper.mapToDto(commentService.findAllPending());
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto update(
            @PositiveOrZero @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentAdminRequest commentDto
    ) {
        log.info("PATCH: Update comment by admin with commentId = {}: {}", commentId, commentDto);
        Comment comment = CommentMapper.mapToEntity(commentDto);
        return CommentMapper.mapToDto(commentService.update(comment, commentId, null, false));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PositiveOrZero @PathVariable Long commentId) {
        log.info("DELETE: Delete comment by admin with commentId = {}", commentId);
        commentService.deleteCommentById(commentId, null, false);
    }
}
