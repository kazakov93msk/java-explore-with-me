package ru.practicum.ewmstats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmstats.model.Hit;
import ru.practicum.ewmstats.model.ViewStats;
import ru.practicum.ewmstats.repository.AppRepository;
import ru.practicum.ewmstats.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatServiceImpl implements StatService {
    private final StatRepository statRep;
    private final AppRepository appRep;

    @Override
    @Transactional
    public Hit createHit(Hit hit) {
        return statRep.save(hit);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return statRep.getStatsUnique(start, end, uris, uris.size());
        }
        return statRep.getStatsNotUnique(start, end, uris, uris.size());
    }
}
