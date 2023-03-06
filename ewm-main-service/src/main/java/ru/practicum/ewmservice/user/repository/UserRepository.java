package ru.practicum.ewmservice.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            "SELECT u " +
            "FROM User u " +
            "WHERE (?2 is null or u.id in ?1)"
    )
    Page<User> findByParams(List<Long> ids, int idsLen, Pageable pageable);
}
