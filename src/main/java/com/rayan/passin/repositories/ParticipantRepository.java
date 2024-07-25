package com.rayan.passin.repositories;

import com.rayan.passin.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

          @Query("SELECT COUNT(a) FROM Participant a WHERE a.event.id = :eventId")
          int getTotalEventAttendees(@Param("eventId") UUID eventId);

          boolean existsByEmail(String email);

          List<Participant> findByEventId(UUID eventId);
}