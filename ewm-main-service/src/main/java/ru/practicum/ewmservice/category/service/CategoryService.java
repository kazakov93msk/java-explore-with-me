package ru.practicum.ewmservice.category.service;

import ru.practicum.ewmservice.category.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll(Long from, Integer size);

    Category findById(Long id);

    Category create(Category category);

    Category update(Long id, Category category);

    void deleteById(Long id);
}
