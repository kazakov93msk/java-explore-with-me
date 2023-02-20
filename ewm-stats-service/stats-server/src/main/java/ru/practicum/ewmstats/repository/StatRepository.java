package ru.practicum.ewmstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmstats.model.Hit;
import ru.practicum.ewmstats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Hit, Integer> {
    @Query(
            value = "SELECT new ru.practicum.ewmstats.model.ViewStats(h.app, h.uri, COUNT(h.ip)) " +
                    "FROM Hit h " +
                    "WHERE h.timestamp >= ?1 " +
                    "  AND h.timestamp <= ?2 " +
                    "  AND h.uri in ?3 " +
                    "GROUP BY h.app, h.uri " +
                    "ORDER BY COUNT(h.ip) DESC ")
    List<ViewStats> getStatsNotUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(
            value = "SELECT new ru.practicum.ewmstats.model.ViewStats(h.app, h.uri, COUNT(DISTINCT(h.ip))) " +
                    "FROM Hit h " +
                    "WHERE h.timestamp >= ?1 " +
                    "  AND h.timestamp <= ?2 " +
                    "  AND h.uri in ?3 " +
                    "GROUP BY h.app, h.uri " +
                    "ORDER BY COUNT(DISTINCT(h.ip)) DESC ")
    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}
