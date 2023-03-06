package ru.practicum.ewmservice.event.property;

public enum EventSort {
    EVENT_DATE("eventDate"),
    VIEWS("views");

    private final String value;

    EventSort(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
