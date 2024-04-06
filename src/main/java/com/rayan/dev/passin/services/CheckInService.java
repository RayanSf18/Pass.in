package com.rayan.dev.passin.services;

import com.rayan.dev.passin.domain.attendee.Attendee;
import com.rayan.dev.passin.domain.checkin.CheckIn;
import com.rayan.dev.passin.domain.checkin.exceptions.CheckInAlreadyExistException;
import com.rayan.dev.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        verifyCheckInExists(attendee.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());

        checkInRepository.save(newCheckIn);
    }

    private void verifyCheckInExists(String attendeeId) {
        Optional<CheckIn> isCheckedIn = getCheckIn(attendeeId);
        if (isCheckedIn.isPresent()) throw new CheckInAlreadyExistException("Attendee already checked in.");

    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return checkInRepository.findByAttendeeId(attendeeId);
    }
}
