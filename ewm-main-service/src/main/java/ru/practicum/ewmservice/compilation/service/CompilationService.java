package ru.practicum.ewmservice.compilation.service;

import ru.practicum.ewmservice.compilation.model.Compilation;

import java.util.List;

public interface CompilationService {

    Compilation findById(Long id);

    List<Compilation> findAll(Boolean pinned, Long from, Integer size);

    Compilation create(Compilation compilation);

    Compilation update(Long id, Compilation compilation);

    void deleteById(Long id);
}
