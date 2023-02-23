package ru.practicum.ewmstats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmstats.dto.HitDto;
import ru.practicum.ewmstats.dto.ViewStatsDto;
import ru.practicum.ewmstats.exception.DateValidationException;
import ru.practicum.ewmstats.mapper.HitMapper;
import ru.practicum.ewmstats.mapper.ViewStatsMapper;
import ru.practicum.ewmstats.model.Hit;
import ru.practicum.ewmstats.service.AppService;
import ru.practicum.ewmstats.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {
    private final StatService statService;
    private final AppService appService;
    private final ViewStatsMapper viewStatsMapper;

    @GetMapping("/stats")
    public List<ViewStatsDto> getViewStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(defaultValue = "") List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique
    ) {
        if (end.isBefore(start)) {
            throw new DateValidationException();
        }
        log.info("GET: Requested stat for period {} - {}, uris: {}, mode: {}", start, end, uris, unique);
        return viewStatsMapper.mapToDto(statService.getStats(start, end, uris, unique));
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto createHit(@Valid @RequestBody HitDto hitDto) {
        log.info("POST: Create hit: {}", hitDto);
        Hit hit = HitMapper.mapToHit(hitDto);
        hit.setApp(appService.findByName(hitDto.getApp()));
        return HitMapper.mapToHitDto(statService.createHit(hit));
    }
}
