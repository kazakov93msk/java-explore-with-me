package ru.practicum.ewmservice.event.stat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewmstats.client.StatsClient;
import ru.practicum.ewmstats.dto.HitDto;
import ru.practicum.ewmstats.dto.ViewStatsDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.ewmservice.utility.Utility.formatter;

@Service
@ComponentScan(basePackages = "ru.practicum.ewmstats")
@Slf4j
public class EventStatService {
    private final Gson gson;
    private final ObjectMapper objectMapper;
    @Value("${ewm-service.name}")
    private String appName;
    private final String eventUrl = "/events/";
    private final StatsClient client;

    public EventStatService(StatsClient statsClient, Gson gson, ObjectMapper objectMapper) {
        this.client = statsClient;
        this.gson = gson;
        this.objectMapper = objectMapper;
    }

    public void sendHit(HttpServletRequest request) {

        HitDto hitDto = HitDto.builder()
                .app(appName)
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        client.createHit(hitDto);
    }

    public Map<Long, Integer> getViewsByEventIds(List<Long> ids, LocalDateTime start) {
        String strStart;
        String strEnd = LocalDateTime.now().format(formatter);

        if (start == null) {
            strStart = "1900-01-01 00:00:00";
        } else {
            strStart = start.format(formatter);
        }


        List<String> uris = new ArrayList<>();
        for (Long id : ids) {
            uris.add(eventUrl + id);
        }

        List<ViewStatsDto> viewsDto = new ArrayList<>();

        log.info("GET: Get to stats. Start = {}, End = {}, uris = {}", strStart, strEnd, uris);

        ResponseEntity<Object> responseEntity = client.getViewStats(strStart, strEnd, uris, false);

        log.info("Response entity: {}", responseEntity);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String jsonBody = gson.toJson(responseEntity.getBody());
            TypeReference<List<ViewStatsDto>> tr = new TypeReference<>() {
            };


            try {
                viewsDto = objectMapper.readValue(jsonBody, tr);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return viewsDto.stream()
                .collect(Collectors.toMap(
                        vs -> Long.parseLong(vs.getUri().replace(eventUrl, "")),
                        vs -> vs.getHits().intValue())
                );
    }

    public Integer getViewsByEventId(Long id, LocalDateTime start) {
        return getViewsByEventIds(List.of(id), start).get(id);
    }
}
