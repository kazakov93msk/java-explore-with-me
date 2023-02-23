package ru.practicum.ewmstats.service;

import ru.practicum.ewmstats.model.App;

public interface AppService {

    App findByName(String name);
}
