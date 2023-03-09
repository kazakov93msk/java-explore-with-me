package ru.practicum.ewmservice.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.service.CategoryService;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.event.dto.UpdateEventUserRequest;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.service.EventService;
import ru.practicum.ewmservice.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewmservice.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.mapper.RequestMapper;
import ru.practicum.ewmservice.request.service.RequestService;
import ru.practicum.ewmservice.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserEventController {
    private final UserService userService;
    private final EventService eventService;
    private final RequestService requestService;
    private final CategoryService categoryService;

    @GetMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> findUserEvents(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @RequestParam(defaultValue = "0") Long from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET: Get events by userId = {}; Pages = {}/{}", userId, from, size);
        return EventMapper.mapToShortDto(eventService.findEventsByUserId(userId, from, size));
    }

    @GetMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto findEventByUserId(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @PathVariable Long eventId
    ) {
        log.info("GET: Get event by ID = {} by userId = {}", eventId, userId);
        return EventMapper.mapToFullDto(eventService.findEventByUserIdAndEventId(userId, eventId));
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestsByInitiatorIdAndEventId(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @PathVariable Long eventId
    ) {
        log.info("GET: Get requests by userId = {} and eventId = {}", userId, eventId);
        return RequestMapper.mapToDto(requestService.findByUserIdAndEventId(userId, eventId));
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(
            @PositiveOrZero @PathVariable Long userId,
            @Valid @RequestBody NewEventDto eventDto
    ) {
        log.info("POST: Create event = {} with user ID = {}", eventDto, userId);
        Event event = EventMapper.mapToEntity(eventDto);
        event.setCategory(categoryService.findById(eventDto.getCategory()));
        event.setInitiator(userService.findById(userId));
        return EventMapper.mapToFullDto(eventService.create(event));
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateRequest(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest requestDto
    ) {
        log.info("PATCH: Update request by userId = {} and eventId = {}", userId, eventId);
        return requestService.updateRequests(userId, eventId, requestDto.getRequestIds(), requestDto.getStatus());
    }

    @PatchMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventUserRequest eventDto
    ) {
        log.info("PATCH: Update event with id = {} and initiator id = {}", eventId, userId);
        Event event = EventMapper.mapToEntity(eventDto);
        return EventMapper.mapToFullDto(eventService.update(userId, eventId, event, eventDto.getCategory()));
    }
}
