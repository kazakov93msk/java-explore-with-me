package ru.practicum.ewmstats.exception;

public class DateValidationException extends RuntimeException {
    public DateValidationException() {
        super("The end time cannot be less or equals than the start time.");
    }
}
