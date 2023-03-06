package ru.practicum.ewmservice.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.property.EventState;
import ru.practicum.ewmservice.event.service.EventService;
import ru.practicum.ewmservice.exception.NotAvailableException;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.exception.OperationAccessException;
import ru.practicum.ewmservice.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewmservice.request.mapper.RequestMapper;
import ru.practicum.ewmservice.request.model.Request;
import ru.practicum.ewmservice.request.property.RequestStatus;
import ru.practicum.ewmservice.request.repository.RequestRepository;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRep;
    private final EventService eventService;
    private final UserService userService;

    @Override
    public Request findById(Long id) {
        return requestRep.findById(id).orElseThrow(
                () -> new NotFoundException(Request.class.getSimpleName(), id)
        );
    }

    @Override
    @Transactional
    public Request cancelRequest(Long userId, Long requestId) {
        Request request = requestRep.findById(requestId).orElseThrow(
                () -> new NotFoundException(Request.class.getSimpleName(), requestId)
        );

        if (!request.getRequester().getId().equals(userId)) {
            throw new OperationAccessException("Only the owner can update the request");
        }

        request.setStatus(RequestStatus.CANCELED);
        return requestRep.save(request);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequests(
            Long userId, Long eventId,
            List<Long> requestIds,
            RequestStatus status
    ) {
        Event event = eventService.findById(eventId);

        validateEvent(event, userId, true);

        List<Request> requests = requestRep.findByIdIn(requestIds);

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            return new EventRequestStatusUpdateResult();
        }

        int vacancyLeft = event.getParticipantLimit() - event.getConfirmedRequests();



        List<Request> confirmedRequests = new ArrayList<>();
        List<Request> rejectedRequests = new ArrayList<>();

        for (Request request : requests) {
            if (!request.getStatus().equals(RequestStatus.PENDING)) {
                throw new NotAvailableException("Request must have status PENDING");
            }

            if (RequestStatus.CONFIRMED.equals(status) && vacancyLeft > 0) {
                confirmedRequests.add(request);
                request.setStatus(RequestStatus.CONFIRMED);
                vacancyLeft--;
            } else {
                request.setStatus(RequestStatus.REJECTED);
                rejectedRequests.add(request);
            }
        }

        if (!confirmedRequests.isEmpty()) {
            requestRep.saveAll(confirmedRequests);
        }
        if (!requests.isEmpty()) {
            requestRep.saveAll(rejectedRequests);
        }

        return new EventRequestStatusUpdateResult(
                RequestMapper.mapToDto(confirmedRequests),
                RequestMapper.mapToDto(rejectedRequests)
        );
    }

    @Override
    @Transactional
    public Request create(Long userId, Long eventId) {
        User user = userService.findById(userId);
        Event event = eventService.findById(eventId);

        if (requestRep.existsByEventIdAndRequesterId(eventId, userId)) {
            throw new OperationAccessException(
                    String.format("Request for eventId = %d from userId = %d already exists", eventId, userId)
            );
        }

        validateEvent(event, userId, false);

        Request request = Request.builder()
                .event(event)
                .requester(user)
                .status(event.getRequestModeration() ? RequestStatus.PENDING : RequestStatus.CONFIRMED)
                .created(LocalDateTime.now())
                .build();

        return requestRep.save(request);
    }

    @Override
    public List<Request> findByUserId(Long userId) {
        return requestRep.findByRequesterId(userId);
    }

    @Override
    public List<Request> findByUserIdAndEventId(Long userId, Long eventId) {
        return requestRep.findByEventIdAndRequesterId(eventId, userId);
    }

    private void validateEvent(Event event, Long userId, Boolean isOwner) {
        if (userId != null) {
            if ((isOwner == null || !isOwner) && event.getInitiator().getId().equals(userId)) {
                throw new OperationAccessException("The initiator of the event cannot add requests for this event");
            } else if (Boolean.TRUE.equals(isOwner) && !event.getInitiator().getId().equals(userId)) {
                throw new NotAvailableException(
                        "Only the owner can update the event",
                        "For the requested operation the conditions are not met."
                );
            }
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new OperationAccessException("It is not possible to apply for an unpublished event");
        }
        if ((event.getRequestModeration() || event.getParticipantLimit() != 0)
                && event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new OperationAccessException("The participant limit has been reached");
        }
    }
}
