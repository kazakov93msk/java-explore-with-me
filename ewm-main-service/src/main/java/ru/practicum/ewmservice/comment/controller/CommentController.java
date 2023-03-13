package ru.practicum.ewmservice.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.dto.NewCommentDto;
import ru.practicum.ewmservice.comment.dto.UpdateCommentUserRequest;
import ru.practicum.ewmservice.comment.mapper.CommentMapper;
import ru.practicum.ewmservice.comment.model.Comment;
import ru.practicum.ewmservice.comment.service.CommentService;
import ru.practicum.ewmservice.event.service.EventService;
import ru.practicum.ewmservice.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final EventService eventService;

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto findById(@PositiveOrZero @PathVariable Long commentId) {
        log.info("GET: Get comment by id = {}", commentId);
        return CommentMapper.mapToDto(commentService.findById(commentId));
    }

    @GetMapping("/users/{commentatorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> findByCommentatorId(
            @PositiveOrZero @PathVariable Long commentatorId,
            @PositiveOrZero @RequestParam(defaultValue = "0") Long from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET: Get all comments");
        return CommentMapper.mapToDto(commentService.findByCommentatorId(commentatorId, from, size));
    }

    @PostMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @PathVariable Long eventId,
            @Valid @RequestBody NewCommentDto commentDto
    ) {
        log.info("POST: Create comment for eventId = {} by userId = {}: {}", eventId, userId, commentDto);
        Comment comment = CommentMapper.mapToEntity(commentDto);
        comment.setCommentator(userService.findById(userId));
        comment.setEvent(eventService.findById(eventId));
        return CommentMapper.mapToDto(commentService.create(comment));
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto update(
            @PositiveOrZero @RequestParam Long userId,
            @PositiveOrZero @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentUserRequest commentDto
    ) {
        log.info("PATCH: Update comment by userId = {} with commentId = {}: {}", userId, commentId, commentDto);
        Comment comment = CommentMapper.mapToEntity(commentDto);
        return CommentMapper.mapToDto(commentService.update(comment, commentId, userId, true));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PositiveOrZero @PathVariable Long commentId,
            @PositiveOrZero @RequestParam Long userId
    ) {
        log.info("DELETE: Delete comment by userId = {} with commentId = {}", userId, commentId);
        commentService.deleteCommentById(commentId, userId, true);
    }
}

