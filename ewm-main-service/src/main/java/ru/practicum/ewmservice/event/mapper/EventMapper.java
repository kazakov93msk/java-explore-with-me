package ru.practicum.ewmservice.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.event.dto.*;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.property.EventState;
import ru.practicum.ewmservice.event.property.StateAction;
import ru.practicum.ewmservice.location.mapper.LocationMapper;
import ru.practicum.ewmservice.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.ewmservice.utility.Utility.nvl;

@UtilityClass
public class EventMapper {

    public static Event mapToEntity(NewEventDto eventDto) {
        return Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .annotation(eventDto.getAnnotation())
                .eventDate(eventDto.getEventDate())
                .createdOn(LocalDateTime.now())
                .location(LocationMapper.mapToEntity(eventDto.getLocation()))
                .paid(eventDto.isPaid())
                .requestModeration(eventDto.isRequestModeration())
                .state(EventState.PENDING)
                .participantLimit(eventDto.getParticipantLimit())
                .build();
    }

    public static Event mapToEntity(UpdateEventAdminRequest eventDto) {
        return Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .annotation(eventDto.getAnnotation())
                .eventDate(eventDto.getEventDate())
                .location(LocationMapper.mapToEntity(eventDto.getLocation()))
                .paid(eventDto.getPaid())
                .requestModeration(eventDto.getRequestModeration())
                .participantLimit(eventDto.getParticipantLimit())
                .state(updateEventState(eventDto.getStateAction()))
                .build();
    }

    public static Event mapToEntity(UpdateEventUserRequest eventDto) {
        return Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .annotation(eventDto.getAnnotation())
                .eventDate(eventDto.getEventDate())
                .location(LocationMapper.mapToEntity(eventDto.getLocation()))
                .paid(eventDto.getPaid())
                .requestModeration(eventDto.getRequestModeration())
                .participantLimit(eventDto.getParticipantLimit())
                .state(updateEventState(eventDto.getStateAction()))
                .build();
    }

    public static EventFullDto mapToFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.mapToDto(event.getCategory()))
                .createdOn(event.getCreatedOn())
                .eventDate(event.getEventDate())
                .publishedOn(event.getPublishedOn())
                .initiator(UserMapper.mapToShortDto(event.getInitiator()))
                .location(LocationMapper.mapToDto(event.getLocation()))
                .paid(event.getPaid())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .participantLimit(event.getParticipantLimit())
                .views(nvl(event.getViews(), 0))
                .confirmedRequests(nvl(event.getConfirmedRequests(), 0))
                .build();
    }

    public static EventShortDto mapToShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.mapToDto(event.getCategory()))
                .eventDate(event.getEventDate())
                .initiator(UserMapper.mapToShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .views(nvl(event.getViews(), 0))
                .confirmedRequests(nvl(event.getConfirmedRequests(), 0))
                .build();
    }

    public static List<EventShortDto> mapToShortDto(List<Event> events) {
        if (events == null) {
            return Collections.emptyList();
        }
        return events.stream().map(EventMapper::mapToShortDto).collect(Collectors.toList());
    }

    public static List<EventFullDto> mapToFullDto(List<Event> events) {
        if (events == null) {
            return Collections.emptyList();
        }
        return events.stream().map(EventMapper::mapToFullDto).collect(Collectors.toList());
    }

    public static Set<EventShortDto> mapToShortDto(Set<Event> events) {
        if (events == null) {
            return Collections.emptySet();
        }
        return events.stream().map(EventMapper::mapToShortDto).collect(Collectors.toSet());
    }

    private static EventState updateEventState(StateAction stateAction) {
        if (stateAction == null) {
            return null;
        }
        switch (stateAction) {
            case PUBLISH_EVENT:
                return EventState.PUBLISHED;
            case REJECT_EVENT:
            case CANCEL_REVIEW:
                return EventState.CANCELED;
            case SEND_TO_REVIEW:
                return EventState.PENDING;
            default:
                return null;
        }
    }
}
