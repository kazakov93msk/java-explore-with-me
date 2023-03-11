package ru.practicum.ewmservice.event.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.property.EventSort;
import ru.practicum.ewmservice.event.property.EventState;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UtilityClass
public class EventUtil {

    public static Comparator<Event> getComparator(EventSort eventSort) {
        if (EventSort.VIEWS.equals(eventSort)) {
            return Comparator.comparing(Event::getViews);
        }
        return Comparator.comparing(Event::getEventDate);
    }

    public static Specification<Event> getEventSpecification(
            List<Long> users,
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            Boolean isPublic
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (isPublic) {
                predicates.add(criteriaBuilder.equal(root.get("state"), EventState.PUBLISHED));
            }

            if (!users.isEmpty()) {
                predicates.add(criteriaBuilder.in(root.get("initiator").get("id")).value(users));
            }

            if (!text.isBlank()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")), "%" + text + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + text + "%")
                ));
            }

            if (!categories.isEmpty()) {
                predicates.add(criteriaBuilder.in(root.get("category").get("id")).value(categories));
            }

            if (paid != null) {
                predicates.add(criteriaBuilder.equal(root.get("paid"), paid));
            }

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart));

            if (rangeEnd != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd));
            }

            if (onlyAvailable) {
                predicates.add(criteriaBuilder.lessThan(root.get("participantLimit"), root.get("confirmedRequests")));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}