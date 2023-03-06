package ru.practicum.ewmservice.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.category.service.CategoryService;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.property.EventSort;
import ru.practicum.ewmservice.event.property.EventState;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.exception.OperationAccessException;
import ru.practicum.ewmservice.location.repository.LocationRepository;
import ru.practicum.ewmservice.request.property.RequestStatus;
import ru.practicum.ewmservice.request.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static ru.practicum.ewmservice.event.util.EventUtil.getEventSpecification;
import static ru.practicum.ewmservice.event.util.EventUtil.getSort;
import static ru.practicum.ewmservice.utility.PageableBuilder.getIdSortedPageable;
import static ru.practicum.ewmservice.utility.PageableBuilder.getPageable;
import static ru.practicum.ewmservice.utility.Utility.getValOrOld;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRep;
    private final RequestRepository requestRep;
    private final LocationRepository locationRep;
    private final CategoryService categoryService;

    @Override
    public Event findById(Long id) {
        Event event = eventRep.findById(id).orElseThrow(
                () -> new NotFoundException(Event.class.getSimpleName(), id)
        );
        return setConfirmedRequests(event);
    }

    @Override
    public List<Event> findById(List<Long> ids) {
        List<Event> events = eventRep.findByIdIn(ids);
        return setConfirmedRequests(events);
    }

    @Override
    public List<Event> findByParams(
            List<Long> users,
            String text, List<Long> categories, Boolean paid,
            LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
            EventSort eventSort, Long from, Integer size, Boolean isPublic
    ) {
        Sort sort = getSort(eventSort);
        Pageable pageable = getPageable(from, size, sort);

        Specification<Event> specification = getEventSpecification(
                users, text, categories, paid, rangeStart, rangeEnd, onlyAvailable, isPublic
        );
        List<Event> events = eventRep.findAll(specification, pageable).getContent();

        return setConfirmedRequests(events);
    }

    @Override
    @Transactional
    public Event create(Event event) {
        if (!locationRep.exists(Example.of(event.getLocation()))) {
            locationRep.save(event.getLocation());
        }
        validateEvent(event, null);
        event.setViews(event.getViews() + 1);
        return eventRep.save(event);
    }

    @Override
    public Event findEventByUserIdAndEventId(Long userId, Long eventId) {
        Event event = eventRep.findByIdAndInitiatorId(eventId, userId).orElseThrow(
                () -> new NotFoundException(Event.class.getSimpleName(), eventId)
        );
        return setConfirmedRequests(event);
    }

    @Override
    public List<Event> findEventsByUserId(Long userId, Long from, Integer size) {
        List<Event> events = eventRep.findByInitiatorId(userId, getIdSortedPageable(from, size)).getContent();
        return setConfirmedRequests(events);
    }

    @Override
    @Transactional
    public Event update(Long userId, Long eventId, Event event, Long categoryId) {
        Event oldEvent = findById(eventId);
        validateEvent(event, userId);
        validateEvent(oldEvent, userId);

        if (EventState.PUBLISHED.equals(oldEvent.getState())) {
            throw new OperationAccessException("Only pending or canceled events can be changed");
        } else if (EventState.CANCELED.equals(oldEvent.getState()) && EventState.PUBLISHED.equals(event.getState())) {
            throw new OperationAccessException("The cancelled event cannot be published");
        }

        if (categoryId != null) {
            oldEvent.setCategory(categoryService.findById(categoryId));
        }
        oldEvent.setAnnotation(getValOrOld(oldEvent.getAnnotation(), event.getAnnotation()));
        oldEvent.setDescription(getValOrOld(oldEvent.getDescription(), event.getDescription()));
        oldEvent.setTitle(getValOrOld(oldEvent.getTitle(), event.getTitle()));
        oldEvent.setLocation(getValOrOld(oldEvent.getLocation(), event.getLocation()));
        oldEvent.setPaid(getValOrOld(oldEvent.getPaid(), event.getPaid()));
        oldEvent.setParticipantLimit(getValOrOld(oldEvent.getParticipantLimit(), event.getParticipantLimit()));
        oldEvent.setRequestModeration(getValOrOld(oldEvent.getRequestModeration(), event.getRequestModeration()));
        oldEvent.setEventDate(getValOrOld(oldEvent.getEventDate(), event.getEventDate()));
        if (userId != null && !EventState.CANCELED.equals(event.getState())) {
            oldEvent.setState(EventState.PENDING);
        } else if (EventState.PUBLISHED.equals(event.getState())) {
            oldEvent.setState(EventState.PUBLISHED);
            oldEvent.setPublishedOn(LocalDateTime.now());
        } else if (EventState.CANCELED.equals(event.getState())) {
            oldEvent.setState(EventState.CANCELED);
            oldEvent.setPublishedOn(null);
        }

        return eventRep.save(oldEvent);
    }

    private List<Event> setConfirmedRequests(List<Event> events) {
        List<Long> ids = events.stream().map(Event::getId).collect(toList());
        Map<Long, Integer> confirmedRequests = requestRep
                .findCountRequestsByEventIdsAndStatus(ids, RequestStatus.CONFIRMED);
        log.info("confirmedRequests: " + confirmedRequests);
        if (confirmedRequests != null && !confirmedRequests.isEmpty()) {
            for (Event event : events) {
                log.info(event.getId().toString());
                event.setConfirmedRequests(confirmedRequests.get(event.getId()));
            }
        }
        return events;
    }

    private Event setConfirmedRequests(Event event) {
        event.setConfirmedRequests(requestRep.findCountRequestsByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED));
        return event;
    }

    private void validateEvent(Event event, Long userId) {
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new OperationAccessException("The start date of the event cannot be earlier than 2 hours later");
        }
        if (userId != null && event.getInitiator() != null && !userId.equals(event.getInitiator().getId())) {
            throw new OperationAccessException("Only the owner can update the event");
        }
    }
}
