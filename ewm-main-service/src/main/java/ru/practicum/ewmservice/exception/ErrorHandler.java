package ru.practicum.ewmservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;

import static java.util.Collections.emptyList;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final NotFoundException e) {
        return buildApiError(e, e.getReason(), e.getStatus());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleOperationAccess(final OperationAccessException e) {
        return buildApiError(e, e.getReason(), e.getStatus());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolation(final DataIntegrityViolationException e) {
        return buildApiError(e, "Integrity constraint has been violated.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConstraintViolation(final ConstraintViolationException e) {
        return buildApiError(e, "Integrity constraint has been violated.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNotAvailable(final NotAvailableException e) {
        return buildApiError(e, e.getReason(), e.getStatus());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlePageArgument(final IllegalPageArgumentException e) {
        return buildApiError(e, e.getReason(), e.getStatus());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(final ValidationException e) {
        return buildApiError(e, "Incorrectly made request.", HttpStatus.BAD_REQUEST);
    }

    private ApiError buildApiError(Exception e, String reason, HttpStatus status) {
        log.error(e.getMessage());
        return ApiError.builder()
                .errors(emptyList())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .reason(reason)
                .status(status)
                .build();
    }
}
