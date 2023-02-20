package ru.practicum.ewmstats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmstats.dto.HitDto;
import ru.practicum.ewmstats.dto.ViewStatsDto;
import ru.practicum.ewmstats.exception.DateValidationException;
import ru.practicum.ewmstats.mapper.HitMapper;
import ru.practicum.ewmstats.mapper.ViewStatsMapper;
import ru.practicum.ewmstats.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewmstats.utility.StatUtil.formatter;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatController {
    private final StatService statService;
    private final HitMapper hitMapper;
    private final ViewStatsMapper viewStatsMapper;

    @GetMapping("/stats")
    public List<ViewStatsDto> getViewStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique
    ) {
        LocalDateTime dtStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dtEnd = LocalDateTime.parse(end, formatter);
        if (dtEnd.isBefore(dtStart)) {
            throw new DateValidationException();
        }
        log.info("GET: Requested stat for period {} - {}, uris: {}, mode: {}", start, end, uris, unique);
        return viewStatsMapper.mapToDto(statService.getStats(dtStart, dtEnd, uris, unique));
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto createHit(@Validated @RequestBody HitDto hitDto) {
        log.info("POST: Create hit: {}", hitDto);
        return hitMapper.mapToHitDto(statService.createHit(hitMapper.mapToHit(hitDto)));
    }
}
