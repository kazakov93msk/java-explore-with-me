package ru.practicum.ewmservice.request.mapper;

import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.model.Request;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmservice.utility.Utility.convertDateTime;

public class RequestMapper {

    public static ParticipationRequestDto mapToDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(convertDateTime(request.getCreated()))
                .status(request.getStatus())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .build();
    }

    public static List<ParticipationRequestDto> mapToDto(List<Request> requests) {
        if (requests == null) {
            return Collections.emptyList();
        }
        return requests.stream().map(RequestMapper::mapToDto).collect(Collectors.toList());
    }
}
