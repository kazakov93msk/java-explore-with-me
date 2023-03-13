package ru.practicum.ewmservice.comment.service;

import ru.practicum.ewmservice.comment.model.Comment;

import java.util.List;

public interface CommentService {

    Comment findByIdInternal(Long commentId);

    Comment findById(Long commentId);

    List<Comment> findByCommentatorId(Long commentatorId, Long from, Integer size);

    Comment create(Comment comment);

    Comment update(Comment comment, Long commentId, Long userId, Boolean isPublic);

    void deleteCommentById(Long commentId, Long userId, Boolean isPublic);

    List<Comment> findAllPending();
}
