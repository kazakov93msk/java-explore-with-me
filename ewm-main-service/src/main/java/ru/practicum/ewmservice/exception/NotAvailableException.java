package ru.practicum.ewmservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotAvailableException extends RuntimeException {
    protected final String reason;
    protected final HttpStatus status = HttpStatus.BAD_REQUEST;

    public NotAvailableException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public NotAvailableException(String message) {
        super(message);
        this.reason = "Incorrectly made request.";
    }
}