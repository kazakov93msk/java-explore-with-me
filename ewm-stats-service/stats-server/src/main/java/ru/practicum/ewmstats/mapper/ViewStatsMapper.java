package ru.practicum.ewmstats.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewmstats.dto.ViewStatsDto;
import ru.practicum.ewmstats.model.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ViewStatsMapper {
    ViewStatsDto mapToDto(ViewStats viewStats);

    List<ViewStatsDto> mapToDto(List<ViewStats> viewStats);
}
