package com.rayan.passin.services;

import com.rayan.passin.exceptions.custom.CheckInAlreadyExistsException;
import com.rayan.passin.models.Checkin;
import com.rayan.passin.models.Participant;
import com.rayan.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CheckInService {

          private static final Logger logger = LoggerFactory.getLogger(CheckInService.class);
          private final CheckInRepository checkInRepository;

          public void registerCheckin(Participant participant) {
                    logger.info("Registering check-in for participant with ID: {}", participant.getId());
                    if (checkInRepository.existsByParticipantId(participant.getId())) {
                              logger.warn("Check-in already exists for participant with ID: {}", participant.getId());
                              throw new CheckInAlreadyExistsException("This participant has already checked in, it is not possible to check in again.");
                    }
                    Checkin checkin = Checkin.builder()
                              .participant(participant)
                              .build();
                    checkInRepository.save(checkin);
                    logger.info("Check-in registered for participant with ID: {}", participant.getId());
          }

          public Optional<Checkin> getCheckInFromParticipant(UUID participantId) {
                    logger.info("Fetching check-in for participant with ID: {}", participantId);
                    return checkInRepository.findByParticipantId(participantId);
          }
}
