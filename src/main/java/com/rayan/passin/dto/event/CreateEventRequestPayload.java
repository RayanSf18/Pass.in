package com.rayan.passin.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Payload for creating a event")
public record CreateEventRequestPayload(

          @Schema(description = "Title of the event", example = "Tech Conference 2024")
          @NotBlank String title,

          @Schema(description = "Details of the event", example = "A conference about the latest in technology.")
          @NotBlank String details,

          @Schema(description = "Maximum number of participants in the event", example = "100")
          @NotNull Integer maximumParticipants

) {
}
