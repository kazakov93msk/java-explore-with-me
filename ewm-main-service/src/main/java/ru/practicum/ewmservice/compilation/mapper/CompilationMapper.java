package ru.practicum.ewmservice.compilation.mapper;

import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.event.mapper.EventMapper;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation mapToEntity(NewCompilationDto compilationDto) {
        return Compilation.builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .build();
    }

    public static Compilation mapToEntity(UpdateCompilationRequest compilationDto) {
        return Compilation.builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .build();
    }

    public static CompilationDto mapToDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(new HashSet<>(EventMapper.mapToShortDto(compilation.getEvents())))
                .build();
    }

    public static List<CompilationDto> mapToDto(List<Compilation> compilations) {
        return compilations.stream().map(CompilationMapper::mapToDto).collect(Collectors.toList());
    }
}
