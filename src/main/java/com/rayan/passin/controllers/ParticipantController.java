package com.rayan.passin.controllers;

import com.rayan.passin.services.ParticipantService;
import com.rayan.passin.dto.participant.ParticipantBadgeResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/participants", produces = {"application/json"})
@Tag(name = "Participant")
public class ParticipantController {

          private static final Logger logger = LoggerFactory.getLogger(ParticipantController.class);
          private final ParticipantService participantService;

          @Operation(summary = "Check-in the participant", method = "POST", description = "This endpoint is used to check in the participant to the specific event.")
          @ApiResponses(value = {
                    @ApiResponse(responseCode = "201", description = "Check-in successful"),
                    @ApiResponse(responseCode = "409", description = "Check-in already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
          })
          @PostMapping(value = "/{participantId}/check-in")
          public ResponseEntity<Void> registerCheckin(
                    @Parameter(description = "Unique identifier of the participant in UUID format", example = "123e4567-e89b-12d3-a456-426614174000")
                    @PathVariable UUID participantId, UriComponentsBuilder uriComponentsBuilder) {
                    logger.info("Registering check-in for participant with ID: {}", participantId);
                    participantService.processCheckin(participantId);
                    var location = uriComponentsBuilder.path("/participants/{participantId}/badge").buildAndExpand(participantId).toUri();
                    return ResponseEntity.created(location).build();
          }

          @Operation(summary = "Search for the participant's badge", method = "GET", description = "This endpoint is used to fetch the participant's badge details.")
          @ApiResponses(value = {
                    @ApiResponse(responseCode = "201", description = "Badge found with successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantBadgeResponsePayload.class))),
                    @ApiResponse(responseCode = "404", description = "Participant not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
          })
          @GetMapping("/{participantId}/badge")
          public ResponseEntity<ParticipantBadgeResponsePayload> getParticipantBadge(
                    @Parameter(description = "Unique identifier of the participant in UUID format", example = "123e4567-e89b-12d3-a456-426614174000")
                    @PathVariable UUID participantId, UriComponentsBuilder uriComponentsBuilder) {
                    logger.info("Fetching badge for participant with ID: {}", participantId);
                    var participant = participantService.getParticipant(participantId);
                    var uri = uriComponentsBuilder.path("/{participantId}/check-in").buildAndExpand(participant.getId()).toUri().toString();
                    return ResponseEntity.ok().body(ParticipantBadgeResponsePayload.toParticipantBadge(participant, uri));
          }
}
