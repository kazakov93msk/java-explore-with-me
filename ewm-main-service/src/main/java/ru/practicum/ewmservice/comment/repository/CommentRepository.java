package ru.practicum.ewmservice.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.comment.model.Comment;
import ru.practicum.ewmservice.comment.property.CommentStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByCommentatorId(Long commentatorId, Pageable pageable);

    List<Comment> findByEventIdAndStatus(Long eventId, CommentStatus status);

    @Query(
            "select c.event.id, c " +
            "from Comment c " +
            "where c.event.id in ?1 " +
            "and c.status = ?2"
    )
    Map<Long, List<Comment>> findByEventIds(List<Long> eventIds, CommentStatus commentStatus);

    List<Comment> findAllByStatus(CommentStatus status);

    Optional<Comment> findByIdAndStatus(Long commentId, CommentStatus status);
}
