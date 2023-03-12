package ru.practicum.ewmservice.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.user.dto.NewUserRequest;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.mapper.UserMapper;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findUsers(
            @RequestParam(defaultValue = "") List<Long> ids,
            @PositiveOrZero @RequestParam(defaultValue = "0") Long from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("GET: Get users by params: ids = {}, from = {}, size = {}", ids, from, size);
        return UserMapper.mapToDto(userService.findByParams(ids, from, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody NewUserRequest userDto) {
        log.info("POST: Create user = {}", userDto);
        User user = UserMapper.mapToEntity(userDto);
        return UserMapper.mapToDto(userService.create(user));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@Positive @PathVariable Long userId) {
        log.info("DELETE: Delete user by id = {}", userId);
        userService.deleteById(userId);
    }
}
