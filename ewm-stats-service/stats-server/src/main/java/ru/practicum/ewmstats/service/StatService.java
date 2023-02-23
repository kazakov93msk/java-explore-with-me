package ru.practicum.ewmstats.service;

import ru.practicum.ewmstats.model.Hit;
import ru.practicum.ewmstats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    Hit createHit(Hit hit);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
