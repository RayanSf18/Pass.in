package com.rayan.passin.repositories;

import com.rayan.passin.models.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckInRepository extends JpaRepository<Checkin, UUID> {

          boolean existsByParticipantId(UUID participantId);

          Optional<Checkin> findByParticipantId(UUID participantId);
}
