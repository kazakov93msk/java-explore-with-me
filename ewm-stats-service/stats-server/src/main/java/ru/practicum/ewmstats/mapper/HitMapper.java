package ru.practicum.ewmstats.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewmstats.dto.HitDto;
import ru.practicum.ewmstats.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {
    Hit mapToHit(HitDto hitDto);

    HitDto mapToHitDto(Hit hit);
}
