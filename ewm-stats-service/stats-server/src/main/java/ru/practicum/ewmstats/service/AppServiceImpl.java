package ru.practicum.ewmstats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmstats.model.App;
import ru.practicum.ewmstats.repository.AppRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AppServiceImpl implements AppService {
    private final AppRepository appRep;

    @Override
    @Transactional
    public App findByName(String name) {
        App app = appRep.findByName(name);
        if (app == null) {
            app = appRep.save(App.builder().name(name).build());
        }
        log.info("{}", app);
        return app;
    }
}
