package ru.practicum.ewmservice.user.service;

import ru.practicum.ewmservice.user.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findByParams(List<Long> ids, Long from, Integer size);

    User create(User user);

    void deleteById(Long id);
}
