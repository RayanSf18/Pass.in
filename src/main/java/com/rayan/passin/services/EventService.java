package com.rayan.passin.services;

import com.rayan.passin.models.Event;
import com.rayan.passin.dto.event.CreateEventRequestPayload;
import com.rayan.passin.exceptions.custom.EventAlreadyExistsException;
import com.rayan.passin.exceptions.custom.EventNotFoundException;
import com.rayan.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@RequiredArgsConstructor
@Service
public class EventService {

          private static final Logger logger = LoggerFactory.getLogger(EventService.class);
          private final EventRepository eventRepository;


          public String registerEvent(CreateEventRequestPayload payload) {
                    logger.info("Registering event with title: {}", payload.title());
                    if (eventRepository.existsByTitle(payload.title())) {
                              logger.warn("Event already exists with title: {}", payload.title());
                              throw new EventAlreadyExistsException("There is already an event registered with this title");
                    }
                    Event event = buildEventFromPayload(payload);
                    event.setSlug(createSlug(payload.title()));
                    eventRepository.save(event);
                    logger.info("Event registered with slug: {}", event.getSlug());
                    return event.getSlug();
          }

          public Event getEventBySlug(String slug) {
                    logger.info("Fetching event with slug: {}", slug);
                    return eventRepository.findBySlug(slug)
                              .orElseThrow(() -> {
                                        logger.warn("Event not found with slug: {}", slug);
                                        return new EventNotFoundException("Event not found with slug: " + slug);
                              });
          }

          private Event buildEventFromPayload(CreateEventRequestPayload payload) {
                    logger.debug("Building event from payload: {}", payload);
                    return Event.builder()
                              .title(payload.title())
                              .details(payload.details())
                              .maximumAttendees(payload.maximumParticipants())
                              .build();
          }

          private String createSlug(String text) {
                    String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
                    String slug = normalized.replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}", "")
                              .replaceAll("[^\\w\\s]", "")
                              .replaceAll("\\s+", "-")
                              .toLowerCase();
                    logger.debug("Created slug: {}", slug);
                    return slug;
          }

}
