package com.rayan.passin.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload for event slug")
public record EventSlugResponsePayload(

          @Schema(description = "Slug of the event", example = "tech-conference-2024")
          String slug
) {
}
