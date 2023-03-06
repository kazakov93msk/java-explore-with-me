package ru.practicum.ewmservice.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.service.CategoryService;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.compilation.service.CompilationService;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.property.EventSort;
import ru.practicum.ewmservice.event.service.EventService;
import ru.practicum.ewmservice.user.dto.NewUserRequest;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.mapper.UserMapper;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AdminController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;

    /* Users */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findUsers(
            @RequestParam(defaultValue = "") List<Long> ids,
            @PositiveOrZero @RequestParam(defaultValue = "0") Long from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET: Get users by params: ids = {}, from = {}, size = {}", ids, from, size);
        return UserMapper.mapToDto(userService.findByParams(ids, from, size));
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody NewUserRequest userDto) {
        log.info("POST: Create user = {}", userDto);
        User user = UserMapper.mapToEntity(userDto);
        return UserMapper.mapToDto(userService.create(user));
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@Positive @PathVariable Long userId) {
        log.info("DELETE: Delete user by id = {}", userId);
        userService.deleteById(userId);
    }

    /* Categories */
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto categoryDto) {
        log.info("POST: Create category = {}", categoryDto);
        Category category = CategoryMapper.mapToEntity(categoryDto);
        return CategoryMapper.mapToDto(categoryService.create(category));
    }

    @PatchMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(
            @PositiveOrZero @PathVariable Long catId,
            @Valid @RequestBody NewCategoryDto categoryDto
    ) {
        log.info("PATCH: Update category with id = {} with data = {}", catId, categoryDto);
        Category category = CategoryMapper.mapToEntity(categoryDto);
        return CategoryMapper.mapToDto(categoryService.update(catId, category));
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PositiveOrZero @PathVariable Long catId) {
        log.info("DELETE: Delete category by id = {}", catId);
        categoryService.deleteById(catId);
    }

    /* Compilations */
    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto compDto) {
        log.info("POST: Create compilation = {}", compDto);
        Compilation compilation = CompilationMapper.mapToEntity(compDto);
        compilation.setEvents(eventService.findById(compDto.getEvents()));
        return CompilationMapper.mapToDto(compilationService.create(compilation));
    }

    @PatchMapping ("/compilations/{compId}")
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

    /* Events */
    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> findEventsByParams(
            @RequestParam(defaultValue = "") List<Long> users,
            @RequestParam(defaultValue = "") String text,
            @RequestParam(defaultValue = "") List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeStart,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) EventSort sort,
            @PositiveOrZero @RequestParam(defaultValue = "0") Long from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET: Get events by params");
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        return EventMapper.mapToFullDto(eventService.findByParams(
                users,
                text.toLowerCase(), categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size, false)
        );
    }

    @PatchMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(
            @PositiveOrZero @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventAdminRequest eventDto
    ) {
        log.info("PATCH: Update event with id = {} by admin", eventId);
        Event event = EventMapper.mapToEntity(eventDto);
        return EventMapper.mapToFullDto(eventService.update(null, eventId, event, eventDto.getCategory()));
    }
}
