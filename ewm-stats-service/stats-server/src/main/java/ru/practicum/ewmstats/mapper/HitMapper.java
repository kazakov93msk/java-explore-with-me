package ru.practicum.ewmstats.mapper;

import ru.practicum.ewmstats.dto.HitDto;
import ru.practicum.ewmstats.model.Hit;

public class HitMapper {
    public static Hit mapToHit(HitDto hitDto) {
        return Hit.builder()
                .id(hitDto.getId())
                .ip(hitDto.getIp())
                .uri(hitDto.getUri())
                .timestamp(hitDto.getTimestamp())
                .build();
    }

    public static HitDto mapToHitDto(Hit hit) {
        return HitDto.builder()
                .id(hit.getId())
                .app(hit.getApp().getName())
                .ip(hit.getIp())
                .uri(hit.getUri())
                .timestamp(hit.getTimestamp())
                .build();
    }
}
