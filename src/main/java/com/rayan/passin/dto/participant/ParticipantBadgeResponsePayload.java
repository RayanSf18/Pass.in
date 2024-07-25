package com.rayan.passin.dto.participant;

import com.rayan.passin.models.Participant;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Participant badge payload")
public record ParticipantBadgeResponsePayload(

          @Schema(description = "Name of the participant", example = "Joe Doe")
          String name,

          @Schema(description = "Email of the participant", example = "joe.doe@gmail.com")
          String email,

          @Schema(description = "Participant check-in url", example = "http://localhost:8080/1381bcd8-bad7-4d95-9cef-49bf1fd02c42/check-in")
          String checkInUrl,

          @Schema(description = "ID of the event", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
          UUID eventId
) {
          public static ParticipantBadgeResponsePayload toParticipantBadge(Participant participant, String checkInUrl) {
                    return new ParticipantBadgeResponsePayload(
                              participant.getName(),
                              participant.getEmail(),
                              checkInUrl,
                              participant.getEvent().getId()
                    );
          }
}
