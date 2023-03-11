package ru.practicum.ewmservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoryRepository;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.exception.OperationAccessException;

import java.util.List;

import static ru.practicum.ewmservice.utility.PageableBuilder.getIdSortedPageable;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRep;
    private final EventRepository eventRep;

    @Override
    public List<Category> findAll(Long from, Integer size) {
        return categoryRep.findAll(getIdSortedPageable(from, size)).getContent();
    }

    @Override
    public Category findById(Long id) {
        return categoryRep.findById(id).orElseThrow(
                () -> new NotFoundException(Category.class.getSimpleName(), id)
        );
    }

    @Override
    @Transactional
    public Category create(Category category) {
        return categoryRep.save(category);
    }

    @Override
    @Transactional
    public Category update(Long id, Category category) {
        Category oldCategory = findById(id);
        if (!category.getName().isBlank()) {
            oldCategory.setName(category.getName());
        }
        return oldCategory;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!categoryRep.existsById(id)) {
            throw new NotFoundException(Category.class.getSimpleName(), id);
        }
        if (eventRep.existsByCategoryId(id)) {
            throw new OperationAccessException("The category is not empty",
                    "For the requested operation the conditions are not met."
            );
        }
        categoryRep.deleteById(id);
    }
}
