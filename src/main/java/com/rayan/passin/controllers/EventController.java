package com.rayan.passin.controllers;

import com.rayan.passin.services.EventService;
import com.rayan.passin.dto.event.EventSlugResponsePayload;
import com.rayan.passin.dto.event.EventResponsePayload;
import com.rayan.passin.dto.event.CreateEventRequestPayload;
import com.rayan.passin.services.ParticipantService;
import com.rayan.passin.dto.participant.CreateParticipantRequestPayload;
import com.rayan.passin.dto.participant.ParticipantDetailResponsePayload;
import com.rayan.passin.dto.participant.ParticipantIdResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/events", produces = {"application/json"})
@Tag(name = "Event")
public class EventController {

          private static final Logger logger = LoggerFactory.getLogger(EventController.class);
          private final EventService eventService;
          private final ParticipantService participantService;

          @Operation(summary = "Register a new event", method = "POST", description = "This endpoint register a new event with the provided details.")
          @ApiResponses(value = {
                    @ApiResponse(responseCode = "201", description = "Event created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventSlugResponsePayload.class))),
                    @ApiResponse(responseCode = "409", description = "Event already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
          })
          @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
          public ResponseEntity<EventSlugResponsePayload> registerEvent(@Valid @RequestBody CreateEventRequestPayload payload) {
                    logger.info("Registering event");
                    String eventSlug = eventService.registerEvent(payload);
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{eventId}").buildAndExpand(eventSlug).toUri();
                    return ResponseEntity.created(location).body(new EventSlugResponsePayload(eventSlug));
          }

          @Operation(summary = "Add a new participant", method = "POST", description = "This endpoint is used to add a new participant to a specific event.")
          @ApiResponses(value = {
                    @ApiResponse(responseCode = "201", description = "Participant added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantIdResponsePayload.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "409", description = "Event already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "422", description = "Event already full", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
          })
          @PostMapping(value = "/{slug}/participants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
          public ResponseEntity<ParticipantIdResponsePayload> addParticipant(
                    @Parameter(description = "Unique identifier of the trip in Slug format", example = "tech-conference-2024")
                    @PathVariable String slug,
                    @Valid @RequestBody CreateParticipantRequestPayload payload, UriComponentsBuilder uriComponentsBuilder) {
                    logger.info("Adding participant to event with slug: {}", slug);
                    UUID participantId = participantService.registerParticipant(slug, payload);
                    var location = uriComponentsBuilder.path("/participants/{participantId}/badge").buildAndExpand(participantId).toUri();
                    return ResponseEntity.created(location).body(new ParticipantIdResponsePayload(participantId));
          }

          @Operation(summary = "Search event details", method = "GET", description = "This endpoint is used to fetch all the important details of the event.")
          @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Event found with successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponsePayload.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
          })
          @GetMapping(value = "/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
          public ResponseEntity<EventResponsePayload> getEventDetails(
                    @Parameter(description = "Unique identifier of the trip in Slug format", example = "tech-conference-2024")
                    @PathVariable String slug) {
                    logger.info("Fetching event details for slug: {}", slug);
                    var event = eventService.getEventBySlug(slug);
                    var totalParticipants = participantService.getNumberOfParticipantsFromEvent(event.getId());
                    return ResponseEntity.ok().body(EventResponsePayload.toEventResponse(event, totalParticipants));
          }

          @Operation(summary = "Search all participants", method = "GET", description = "This endpoint is used to search for all participants of a specific event.")
          @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Participants found with successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantDetailResponsePayload.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
          })
          @GetMapping(value = "/{slug}/participants")
          public ResponseEntity<List<ParticipantDetailResponsePayload>> getParticipants(
                    @Parameter(description = "Unique identifier of the trip in Slug format", example = "tech-conference-2024")
                    @PathVariable String slug) {
                    logger.info("Fetching participants for event with slug: {}", slug);
                    List<ParticipantDetailResponsePayload> participants = participantService.getParticipantsDetailsFromEvent(slug);
                    return ResponseEntity.ok().body(participants);
          }
}
