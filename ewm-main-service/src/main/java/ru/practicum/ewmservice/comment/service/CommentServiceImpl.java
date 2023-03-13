package ru.practicum.ewmservice.comment.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.comment.model.Comment;
import ru.practicum.ewmservice.comment.property.CommentStatus;
import ru.practicum.ewmservice.comment.repository.CommentRepository;
import ru.practicum.ewmservice.event.property.EventState;
import ru.practicum.ewmservice.exception.NotAvailableException;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.user.service.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.practicum.ewmservice.utility.PageableBuilder.getCreatedSortedPageable;
import static ru.practicum.ewmservice.utility.Utility.getValOrOld;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRep;
    private final UserService userService;

    @Override
    public Comment findByIdInternal(Long commentId) {
        return commentRep.findById(commentId).orElseThrow(
                () -> new NotFoundException(Comment.class.getSimpleName(), commentId)
        );
    }

    @Override
    public Comment findById(Long commentId) {
        return commentRep.findByIdAndStatus(commentId, CommentStatus.APPROVED).orElseThrow(
                () -> new NotFoundException(Comment.class.getSimpleName(), commentId)
        );
    }

    @Override
    public List<Comment> findByCommentatorId(Long commentatorId, Long from, Integer size) {
        userService.findById(commentatorId);

        return commentRep.findByCommentatorId(commentatorId, getCreatedSortedPageable(from, size)).getContent();
    }

    @Override
    @Transactional
    public Comment create(Comment comment) {
        validateComment(comment, true);

        return commentRep.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Comment comment, Long commentId, Long userId, Boolean isPublic) {
        Comment oldComment = findByIdInternal(commentId);
        validateComment(oldComment, isPublic);

        if (isPublic && !oldComment.getCommentator().getId().equals(userId)) {
            throw new NotAvailableException("Only the commentator or administrator can update a comment");
        }

        oldComment.setAnnotation(getValOrOld(oldComment.getAnnotation(), comment.getAnnotation()));
        oldComment.setText(getValOrOld(oldComment.getText(), comment.getText()));

        if (isPublic) {
            oldComment.setStatus(CommentStatus.PENDING);
            oldComment.setModified(LocalDateTime.now());
        } else {
            oldComment.setStatus(getValOrOld(oldComment.getStatus(), comment.getStatus()));
        }

        return commentRep.save(oldComment);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long commentId, Long userId, Boolean isPublic) {
        Comment comment = findByIdInternal(commentId);

        if (isPublic
                && !comment.getCommentator().getId().equals(userId)
                && !comment.getEvent().getInitiator().getId().equals(userId)
        ) {
            throw new NotAvailableException("Only the commentator, event owner, or administrator can delete a comment");
        }

        commentRep.deleteById(commentId);
    }

    @Override
    public List<Comment> findAllPending() {
        return commentRep.findAllByStatus(CommentStatus.PENDING);
    }

    private void validateComment(Comment comment, Boolean isPublic) {
        if (!EventState.PUBLISHED.equals(comment.getEvent().getState())) {
            throw new NotAvailableException("It is not possible to apply for an unpublished event");
        }
        // Для тестов сравнение установлено на 2 секунды.
        // Фактические можно разрешить менять комментарии в течение, например, получаса
        long allowedTimeAmount = 2L;
        ChronoUnit timeUnits = ChronoUnit.SECONDS;

        if (isPublic && comment.getCreated() != null
                && comment.getCreated().plus(allowedTimeAmount, timeUnits).isBefore(LocalDateTime.now())) {
            throw new NotAvailableException(
                    String.format("It is forbidden to change a comment after %d, %s", allowedTimeAmount, timeUnits)
            );
        }
    }
}
