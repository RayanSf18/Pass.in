package com.rayan.dev.passin.services;

import com.rayan.dev.passin.domain.attendee.Attendee;
import com.rayan.dev.passin.domain.event.Event;
import com.rayan.dev.passin.domain.event.exceptions.EventFullException;
import com.rayan.dev.passin.domain.event.exceptions.EventNotFoundException;
import com.rayan.dev.passin.dto.attendee.AttendeeIdDTO;
import com.rayan.dev.passin.dto.attendee.AttendeeRequestDTO;
import com.rayan.dev.passin.dto.event.EventIdDTO;
import com.rayan.dev.passin.dto.event.EventRequestDTO;
import com.rayan.dev.passin.dto.event.EventResponseDTO;
import com.rayan.dev.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String id) {
        Event event = getEventById(id);
        List<Attendee> attendees = attendeeService.getAllAttendeesFromEvent(id);
        return new EventResponseDTO(event, attendees.size());
    }

    private Event getEventById(String eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }

    public EventIdDTO createEvent(EventRequestDTO eventRequestDTO) {
        Event newEvent = new Event();
        newEvent.setTitle(eventRequestDTO.title());
        newEvent.setDetails(eventRequestDTO.details());
        newEvent.setMaximumAttendees(eventRequestDTO.maximumAttendees());
        newEvent.setSlug(createSlug(eventRequestDTO.title()));

        this.eventRepository.save(newEvent);
        return new EventIdDTO(newEvent.getId());
    }

    /**
     * Converts the input text into a "slug" representation, useful for creating URL-friendly strings.
     * This involves normalizing the text, removing diacritical marks (such as accents), replacing
     * non-word characters and spaces with hyphens, and converting the text to lower case.
     *
     * @param text The original text to be converted into a slug.
     * @return A slug version of the input text.
     */
    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "").replaceAll("[^\\w\\s]", "").replaceAll("\\s+", "-").toLowerCase();
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
        attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);
        Event event = getEventById(eventId);
        List<Attendee> attendees = attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendees.size()) throw new EventFullException("Event is full");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());

        attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }
}
