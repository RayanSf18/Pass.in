package com.rayan.dev.passin.services;

import com.rayan.dev.passin.domain.attendee.Attendee;
import com.rayan.dev.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import com.rayan.dev.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.rayan.dev.passin.domain.checkin.CheckIn;
import com.rayan.dev.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.rayan.dev.passin.dto.attendee.AttendeeDetails;
import com.rayan.dev.passin.dto.attendee.AttendeesListResponseDTO;
import com.rayan.dev.passin.dto.attendee.AttendeeBadgeDTO;
import com.rayan.dev.passin.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
       return attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId) {
        List<Attendee> attendees = getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetails = attendees.stream().map(attendee -> {
            Optional<CheckIn> checkIn = checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetails);
    }

    public Attendee registerAttendee(Attendee newAttendee) {
        attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = attendeeRepository.findByEventIdAndEmail(eventId, email);
        if (isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered");

    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = getAttendee(attendeeId);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();
        return new AttendeeBadgeResponseDTO(new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId()));
    }

    public void checkInAttendee(String attendeeId) {
        Attendee attendee = getAttendee(attendeeId);
        checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId) {
       return attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));
    }
}
