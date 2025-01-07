package com.example.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class CountdownService {

    private static final ZonedDateTime TARGET_DATE = ZonedDateTime.of(
            2027, 11, 27, 0, 0, 0, 0,
            ZoneId.of("Europe/Amsterdam")
    );

    public CountdownResponse getRemainingTime() {
        Instant now = Instant.now();
        Instant targetInstant = TARGET_DATE.toInstant();
        Duration remainingDuration = Duration.between(now, targetInstant);

        long days = remainingDuration.toDays();
        long hours = remainingDuration.toHours() % 24;
        long minutes = remainingDuration.toMinutes() % 60;
        long seconds = remainingDuration.getSeconds() % 60;

        return new CountdownResponse(days, hours, minutes, seconds, targetInstant.toEpochMilli());
    }

    @Getter
    public static class CountdownResponse {
        private final long days;
        private final long hours;
        private final long minutes;
        private final long seconds;
        private final long targetTimestamp;

        public CountdownResponse(long days, long hours, long minutes, long seconds, long targetTimestamp) {
            this.days = days;
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
            this.targetTimestamp = targetTimestamp;
        }
    }
}