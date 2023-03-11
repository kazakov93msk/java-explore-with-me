package ru.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.property.EventSort;
import ru.practicum.ewmservice.event.service.EventService;
import ru.practicum.ewmservice.event.stat.EventStatService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventController {

    private final EventService eventService;
    private final EventStatService statService;

    @GetMapping
    public List<EventShortDto> findEventsByParams(
            @RequestParam(defaultValue = "") String text,
            @RequestParam(defaultValue = "") List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeStart,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(defaultValue = "EVENT_DATE") EventSort sort,
            @PositiveOrZero @RequestParam(defaultValue = "0") Long from,
            @Positive @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request
    ) {
        log.info("GET: Get events by params");
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        statService.sendHit(request);
        return EventMapper.mapToShortDto(eventService.findByParams(
                Collections.emptyList(),
                text.toLowerCase(), categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size, true)
        );
    }

    @GetMapping("/{eventId}")
    public EventFullDto findById(
            @PositiveOrZero @PathVariable Long eventId,
            HttpServletRequest request
    ) {
        log.info("GET: Get event by id = {}", eventId);
        statService.sendHit(request);
        return EventMapper.mapToFullDto(eventService.findById(eventId));
    }
}
