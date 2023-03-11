package ru.practicum.ewmservice.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.compilation.repository.CompilationRepository;
import ru.practicum.ewmservice.exception.NotFoundException;

import java.util.List;

import static ru.practicum.ewmservice.utility.PageableBuilder.getIdSortedPageable;
import static ru.practicum.ewmservice.utility.Utility.getValOrOld;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRep;

    @Override
    public Compilation findById(Long id) {
        return compilationRep.findById(id).orElseThrow(
                () -> new NotFoundException(Compilation.class.getSimpleName(), id)
        );
    }

    @Override
    public List<Compilation> findAll(Boolean pinned, Long from, Integer size) {
        if (pinned == null) {
            return compilationRep.findAll(getIdSortedPageable(from, size)).getContent();
        }
        return compilationRep.findByPinned(pinned, getIdSortedPageable(from, size)).getContent();
    }

    @Override
    @Transactional
    public Compilation create(Compilation compilation) {
        return compilationRep.save(compilation);
    }

    @Override
    @Transactional
    public Compilation update(Long id, Compilation compilation) {
        Compilation oldCompilation = findById(id);
        oldCompilation.setPinned(getValOrOld(oldCompilation.getPinned(), compilation.getPinned()));
        oldCompilation.setEvents(getValOrOld(oldCompilation.getEvents(), compilation.getEvents()));
        oldCompilation.setTitle(getValOrOld(oldCompilation.getTitle(), compilation.getTitle()));
        return compilationRep.save(oldCompilation);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!compilationRep.existsById(id)) {
            throw new NotFoundException(Compilation.class.getSimpleName(), id);
        }
        compilationRep.deleteById(id);
    }
}
