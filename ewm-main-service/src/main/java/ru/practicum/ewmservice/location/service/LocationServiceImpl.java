package ru.practicum.ewmservice.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.location.model.Location;
import ru.practicum.ewmservice.location.repository.LocationRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRep;

    @Override
    public Location findById(Long id) {
        return locationRep.findById(id).orElseThrow(
                () -> new NotFoundException(Location.class.getSimpleName(), id)
        );
    }

    @Override
    @Transactional
    public Location create(Location location) {
        return locationRep.save(location);
    }
}
