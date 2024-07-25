package com.rayan.passin.dto.participant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload for creating a participant")
public record CreateParticipantRequestPayload(

          @Schema(description = "Name of the participant", example = "Joe Doe")
          @NotBlank String name,

          @Schema(description = "Email of the participant", example = "joe.doe@gmail.com")
          @Email String email

) {
}
