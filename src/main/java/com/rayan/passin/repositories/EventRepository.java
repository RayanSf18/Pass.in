package com.rayan.passin.repositories;

import com.rayan.passin.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

          boolean existsByTitle(String title);

          Optional<Event> findBySlug(String slug);
}