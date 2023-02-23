package ru.practicum.ewmstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmstats.model.App;

public interface AppRepository extends JpaRepository<App, Integer> {

    App findByName(String name);
}
