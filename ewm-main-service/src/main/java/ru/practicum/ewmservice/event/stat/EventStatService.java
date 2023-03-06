package ru.practicum.ewmservice.event.stat;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.practicum.ewmstats.client.StatsClient;
import ru.practicum.ewmstats.dto.HitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
//@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.practicum.ewmstats")
public class EventStatService {
    private final StatsClient client;

    public EventStatService(StatsClient statsClient) {
        this.client = statsClient;
    }

    public void sendHit(HttpServletRequest request) {
        HitDto hitDto = HitDto.builder()
                .app("main-service")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        client.createHit(hitDto).getStatusCode();
    }
}
