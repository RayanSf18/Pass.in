package com.rayan.passin.dto.participant;

import com.rayan.passin.models.Participant;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Payload for participant details")
public record ParticipantDetailResponsePayload(

          @Schema(description = "ID of the participant", example = "123e4567-e89b-12d3-a456-426614174000")
          UUID participantId,

          @Schema(description = "Name of the participant", example = "Joe Doe")
          String name,

          @Schema(description = "Email of the participant", example = "joe.doe@gmail.com")
          String email,

          @Schema(description = "Participant registration date", example = "2024-07-25T20:00:00")
          LocalDateTime createdAt,

          @Schema(description = "Check-in date", example = "2024-07-25T20:00:00")
          LocalDateTime checkInCreatedAt
) {
         public static ParticipantDetailResponsePayload toParticipantDetailResponse(Participant participant, LocalDateTime checkInCreatedAt) {
                   return new ParticipantDetailResponsePayload(
                             participant.getId(),
                             participant.getName(),
                             participant.getEmail(),
                             participant.getCreatedAt(),
                             checkInCreatedAt
                   );
         }
}
