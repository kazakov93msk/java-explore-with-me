package ru.practicum.ewmservice.utility;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.ewmservice.exception.IllegalPageArgumentException;

@UtilityClass
public class PageableBuilder {
    public static final Integer DEFAULT_SIZE = 10;
    private static final Long DEFAULT_START = 1L;
    public static final Sort ID_ASC = Sort.by(Sort.Direction.ASC, "id");
    public static final Sort CREATED_DESC = Sort.by(Sort.Direction.DESC, "created");

    public static Pageable getPageable(Long start, Integer size, Sort sort) {
        if (start == null) {
            start = DEFAULT_START;
        } else if (start < 0) {
            throw new IllegalPageArgumentException("Parameter 'start' cannot by less than 0.", "from");
        }

        if (size == null) {
            size = DEFAULT_SIZE;
        } else if (size < 0) {
            throw new IllegalPageArgumentException("Parameter 'size' cannot be less than 0.", "size");
        }

        if (sort == null) {
            throw new IllegalPageArgumentException("Parameter 'sort' cannot be is null.", "sort");
        }

        int pages = Math.toIntExact(start / size);
        return PageRequest.of(pages, size, sort);
    }

    public static Pageable getIdSortedPageable(Long start, Integer size) {
        return getPageable(start, size, ID_ASC);
    }

    public static Pageable getCreatedSortedPageable(Long start, Integer size) {
        return getPageable(start, size, CREATED_DESC);
    }
}
