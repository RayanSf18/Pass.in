package com.rayan.dev.passin.controllers;

import com.rayan.dev.passin.dto.attendee.AttendeeIdDTO;
import com.rayan.dev.passin.dto.attendee.AttendeeRequestDTO;
import com.rayan.dev.passin.dto.attendee.AttendeesListResponseDTO;
import com.rayan.dev.passin.dto.event.EventIdDTO;
import com.rayan.dev.passin.dto.event.EventRequestDTO;
import com.rayan.dev.passin.dto.event.EventResponseDTO;
import com.rayan.dev.passin.services.AttendeeService;
import com.rayan.dev.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO eventDTO =  eventService.getEventDetail(id);
        return ResponseEntity.ok().body(eventDTO);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id) {
        AttendeesListResponseDTO attendeesListResponseDTO =  attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok().body(attendeesListResponseDTO);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventDTO = eventService.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventDTO.id()).toUri();
        return ResponseEntity.created(uri).body(eventDTO);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeIdDTO attendeeIdDTO = eventService.registerAttendeeOnEvent(eventId, body);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();
        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }


}
