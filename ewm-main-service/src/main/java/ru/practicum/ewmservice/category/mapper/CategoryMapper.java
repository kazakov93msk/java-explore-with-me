package ru.practicum.ewmservice.category.mapper;

import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.model.Category;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryDto mapToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category mapToEntity(NewCategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }

    public static List<CategoryDto> mapToDto(List<Category> categories) {
        if (categories == null) {
            return Collections.emptyList();
        }
        return categories.stream().map(CategoryMapper::mapToDto).collect(Collectors.toList());
    }
}
