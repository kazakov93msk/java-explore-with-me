package ru.practicum.ewmservice.request.service;

import ru.practicum.ewmservice.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewmservice.request.model.Request;
import ru.practicum.ewmservice.request.property.RequestStatus;

import java.util.List;

public interface RequestService {
    Request findById(Long id);

    Request cancelRequest(Long userId, Long requestId);

    EventRequestStatusUpdateResult updateRequests(Long userId, Long eventId, List<Long> requestIds, RequestStatus status);

    Request create(Long userId, Long eventId);

    List<Request> findByUserId(Long userId);

    List<Request> findByUserIdAndEventId(Long userId, Long eventId);
}
