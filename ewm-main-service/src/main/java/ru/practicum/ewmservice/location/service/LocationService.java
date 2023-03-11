package ru.practicum.ewmservice.location.service;

import ru.practicum.ewmservice.location.model.Location;

public interface LocationService {

    Location findById(Long id);

    Location create(Location location);
}
