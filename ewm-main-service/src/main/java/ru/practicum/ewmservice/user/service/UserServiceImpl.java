package ru.practicum.ewmservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.repository.UserRepository;

import java.util.List;

import static ru.practicum.ewmservice.utility.PageableBuilder.getIdSortedPageable;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRep;

    @Override
    public User findById(Long id) {
        return userRep.findById(id).orElseThrow(
                () -> new NotFoundException(User.class.getSimpleName(), id)
        );
    }

    @Override
    public List<User> findByParams(List<Long> ids, Long from, Integer size) {
        return userRep.findByParams(ids, ids.size(), getIdSortedPageable(from, size)).getContent();
    }

    @Override
    @Transactional
    public User create(User user) {
        return userRep.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!userRep.existsById(id)) {
            throw new NotFoundException(User.class.getSimpleName(), id);
        }
        userRep.deleteById(id);
    }
}
