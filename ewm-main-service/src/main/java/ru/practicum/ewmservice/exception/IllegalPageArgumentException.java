package ru.practicum.ewmservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IllegalPageArgumentException extends RuntimeException {
    private final String reason;
    private final HttpStatus status;

    public IllegalPageArgumentException(String message, String param) {
        super(message);
        this.reason = String.format("Incorrect parameter: %s.", param);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
