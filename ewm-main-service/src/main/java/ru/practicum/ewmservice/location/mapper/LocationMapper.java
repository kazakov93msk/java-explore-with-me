package ru.practicum.ewmservice.location.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.location.dto.LocationDto;
import ru.practicum.ewmservice.location.model.Location;

@UtilityClass
public class LocationMapper {

    public static Location mapToEntity(LocationDto locationDto) {
        if (locationDto == null) {
            return null;
        }
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

    public static LocationDto mapToDto(Location location) {
        if (location == null) {
            return null;
        }
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
