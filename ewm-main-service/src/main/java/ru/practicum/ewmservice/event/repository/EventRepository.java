package ru.practicum.ewmservice.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findByIdIn(List<Long> eventIds);

    Boolean existsByCategoryId(Long id);

    Page<Event> findByInitiatorId(Long id, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);
}
