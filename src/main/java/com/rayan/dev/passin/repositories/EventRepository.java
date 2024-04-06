package com.rayan.dev.passin.repositories;

import com.rayan.dev.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
