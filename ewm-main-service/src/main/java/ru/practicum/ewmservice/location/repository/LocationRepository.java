package ru.practicum.ewmservice.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
