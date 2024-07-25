package com.rayan.passin.dto.event;

import com.rayan.passin.models.Event;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Payload for event details")
public record EventResponsePayload(

          @Schema(description = "ID of the event", example = "123e4567-e89b-12d3-a456-426614174000")
          UUID id,

          @Schema(description = "Title of the event", example = "Tech Conference 2024")
          String title,

          @Schema(description = "Details of the event", example = "A conference about the latest in technology.")
          String details,

          @Schema(description = "Slug of the event", example = "tech-conference-2024")
          String slug,

          @Schema(description = "Maximum of the participants", example = "100")
          Integer maximumParticipants,

          @Schema(description = "Total registered participants", example = "55")
          int totalParticipantsAdded
) {
          public static EventResponsePayload toEventResponse(Event event, int totalAttendees) {
                    return new EventResponsePayload(
                              event.getId(),
                              event.getTitle(),
                              event.getDetails(),
                              event.getSlug(),
                              event.getMaximumAttendees(),
                              totalAttendees
                    );
          }
}
