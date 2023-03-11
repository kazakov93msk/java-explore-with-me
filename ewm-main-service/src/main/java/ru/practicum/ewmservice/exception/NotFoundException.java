package ru.practicum.ewmservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
    protected final String reason;
    protected final HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundException(String clsName, Long id) {
        super(String.format("%s with ID = %d not found", clsName, id));
        this.reason = "The required object was not found.";
    }
}
