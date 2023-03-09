package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.property.EventSort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventService {

    Event findById(Long id);

    Set<Event> findById(Set<Long> ids);

    List<Event> findByParams(
            List<Long> users,
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            EventSort sort,
            Long from,
            Integer size,
            Boolean isPublic
    );

    Event create(Event event);

    Event findEventByUserIdAndEventId(Long userId, Long eventId);

    List<Event> findEventsByUserId(Long userId, Long from, Integer size);

    Event update(Long userId, Long eventId, Event event, Long categoryId);
}
