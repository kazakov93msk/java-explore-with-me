package ru.practicum.ewmservice.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.mapper.RequestMapper;
import ru.practicum.ewmservice.request.service.RequestService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserRequestController {

    private final RequestService requestService;

    @GetMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestsByUserId(
            @PositiveOrZero @PathVariable Long userId
    ) {
        log.info("GET: Get requests by userId = {}", userId);
        return RequestMapper.mapToDto(requestService.findByUserId(userId));
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @RequestParam Long eventId
    ) {
        log.info("POST: Create request by userId = {} and eventId = {}", userId, eventId);
        return RequestMapper.mapToDto(requestService.create(userId, eventId));
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelRequest(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @PathVariable Long requestId
    ) {
        log.info("PATCH: Cancel request by userId = {} and requestId = {}", userId, requestId);
        return RequestMapper.mapToDto(requestService.cancelRequest(userId, requestId));
    }
}
