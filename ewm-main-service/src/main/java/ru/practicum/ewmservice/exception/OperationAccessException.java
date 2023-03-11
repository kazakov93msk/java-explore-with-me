package ru.practicum.ewmservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OperationAccessException extends RuntimeException {
    protected final String reason;
    protected final HttpStatus status = HttpStatus.CONFLICT;

    public OperationAccessException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public OperationAccessException(String message) {
        super(message);
        this.reason = "For the requested operation the conditions are not met.";
    }
}
