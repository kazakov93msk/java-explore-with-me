package ru.practicum.ewmstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmstats.model.Hit;
import ru.practicum.ewmstats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Hit, Integer> {
    @Query(
            value = "SELECT new ru.practicum.ewmstats.model.ViewStats(a.name, h.uri, COUNT(h.ip)) " +
                    "FROM Hit h " +
                    "JOIN h.app a " +
                    "WHERE h.timestamp >= ?1 " +
                    "  AND h.timestamp <= ?2 " +
                    "  AND (?4 = 0 or h.uri in ?3) " +
                    "GROUP BY a.name, h.uri " +
                    "ORDER BY COUNT(h.ip) DESC ")
    List<ViewStats> getStatsNotUnique(LocalDateTime start, LocalDateTime end, List<String> uris, int urisLen);

    @Query(
            value = "SELECT new ru.practicum.ewmstats.model.ViewStats(a.name, h.uri, COUNT(DISTINCT(h.ip))) " +
                    "FROM Hit h " +
                    "JOIN h.app a " +
                    "WHERE h.timestamp >= ?1 " +
                    "  AND h.timestamp <= ?2 " +
                    "  AND (?4 is null or h.uri in ?3) " +
                    "GROUP BY a.name, h.uri " +
                    "ORDER BY COUNT(DISTINCT(h.ip)) DESC ")
    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris, int urisLen);
}
