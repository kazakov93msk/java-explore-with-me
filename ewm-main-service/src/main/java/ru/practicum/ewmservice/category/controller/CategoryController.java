package ru.practicum.ewmservice.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAll(
            @PositiveOrZero @RequestParam(defaultValue = "0") Long from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET: Get categories by params: from = {}, size = {}", from, size);
        return CategoryMapper.mapToDto(categoryService.findAll(from, size));
    }

    @GetMapping("/{catId}")
    public CategoryDto findById(@PositiveOrZero @PathVariable Long catId) {
        log.info("GET: Get category by id = {}", catId);
        return CategoryMapper.mapToDto(categoryService.findById(catId));
    }
}
