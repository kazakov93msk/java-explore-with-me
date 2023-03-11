package ru.practicum.ewmservice.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.compilation.service.CompilationService;
import ru.practicum.ewmservice.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AdminCompilationController {

    private final EventService eventService;
    private final CompilationService compilationService;

    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto compDto) {
        log.info("POST: Create compilation = {}", compDto);
        Compilation compilation = CompilationMapper.mapToEntity(compDto);
        if (compDto.getEvents() != null && !compDto.getEvents().isEmpty()) {
            compilation.setEvents(eventService.findById(compDto.getEvents()));
        }
        return CompilationMapper.mapToDto(compilationService.create(compilation));
    }

    @PatchMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(
            @PositiveOrZero @PathVariable Long compId,
            @Valid @RequestBody UpdateCompilationRequest compDto
    ) {
        log.info("PATCH: Update compilation with id = {} with data = {}", compId, compDto);
        Compilation compilation = CompilationMapper.mapToEntity(compDto);
        compilation.setEvents(eventService.findById(compDto.getEvents()));
        return CompilationMapper.mapToDto(compilationService.update(compId, compilation));
    }

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationById(@PositiveOrZero @PathVariable Long compId) {
        log.info("DELETE: Delete compilation by id = {}", compId);
        compilationService.deleteById(compId);
    }
}
