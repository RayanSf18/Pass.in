package com.rayan.passin.services;

import com.rayan.passin.models.Checkin;
import com.rayan.passin.models.Event;
import com.rayan.passin.exceptions.custom.EventFullException;
import com.rayan.passin.exceptions.custom.ParticipantNotFoundException;
import com.rayan.passin.exceptions.custom.ParticipantAlreadyExistsException;
import com.rayan.passin.models.Participant;
import com.rayan.passin.dto.participant.CreateParticipantRequestPayload;
import com.rayan.passin.dto.participant.ParticipantDetailResponsePayload;
import com.rayan.passin.repositories.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ParticipantService {

          private static final Logger logger = LoggerFactory.getLogger(ParticipantService.class);
          private final ParticipantRepository participantRepository;
          private final EventService eventService;
          private final CheckInService checkInService;

          public int getNumberOfParticipantsFromEvent(UUID eventId) {
                    logger.info("Fetching number of participants for event with ID: {}", eventId);
                    return participantRepository.getTotalEventAttendees(eventId);
          }

          public UUID registerParticipant(String slug, CreateParticipantRequestPayload payload) {
                    logger.info("Registering participant for event with slug: {}", slug);
                    Event event = eventService.getEventBySlug(slug);
                    if (participantRepository.findByEventId(event.getId()).size() >= event.getMaximumAttendees()) {
                              logger.warn("Event is full: {}", slug);
                              throw new EventFullException("This event is already full, it is no longer possible to participate.");
                    }
                    if (participantRepository.existsByEmail(payload.email())) {
                              logger.warn("Participant already exists with email: {}", payload.email());
                              throw new ParticipantAlreadyExistsException("There is already a participant registered with this email");
                    }
                    Participant participant = buildParticipantFromPayload(payload, event);
                    participantRepository.save(participant);
                    logger.info("Participant registered with ID: {}", participant.getId());
                    return participant.getId();
          }

          public List<ParticipantDetailResponsePayload> getParticipantsDetailsFromEvent(String eventSlug) {
                    logger.info("Fetching participants details for event with slug: {}", eventSlug);
                    Event event = eventService.getEventBySlug(eventSlug);
                    List<Participant> participants = participantRepository.findByEventId(event.getId());
                    return participants.stream()
                              .map(participant -> {
                                        Optional<Checkin> checkin = checkInService.getCheckInFromParticipant(participant.getId());
                                        LocalDateTime checkinTime = checkin.map(Checkin::getCreatedAt).orElse(null);
                                        return ParticipantDetailResponsePayload.toParticipantDetailResponse(participant, checkinTime);
                              })
                              .toList();
          }

          public Participant getParticipant(UUID participantId) {
                    logger.info("Fetching participant with ID: {}", participantId);
                    return participantRepository.findById(participantId)
                              .orElseThrow(() -> {
                                        logger.warn("Participant not found with ID: {}", participantId);
                                        return new ParticipantNotFoundException("Participant not found with id: " + participantId);
                              });
          }

          public void processCheckin(UUID participantId) {
                    logger.info("Processing check-in for participant with ID: {}", participantId);
                    Participant participant = getParticipant(participantId);
                    checkInService.registerCheckin(participant);
          }

          private Participant buildParticipantFromPayload(CreateParticipantRequestPayload payload, Event eventDb) {
                    logger.debug("Building participant from payload: {}", payload);
                    return Participant.builder()
                              .name(payload.name())
                              .email(payload.email())
                              .event(eventDb)
                              .build();
          }

}
