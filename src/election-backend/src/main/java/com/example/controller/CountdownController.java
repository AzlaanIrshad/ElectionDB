package com.example.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/countdown")
public class CountdownController {

    private static final ZonedDateTime TARGET_DATE = ZonedDateTime.of(
            2027, 11, 27, 0, 0, 0, 0,
            ZoneId.systemDefault()
    );

    @GetMapping("/")
    public CountdownResponse getCountdownTime() {
        Instant now = Instant.now();
        Instant targetInstant = TARGET_DATE.toInstant();
        Duration remainingDuration = Duration.between(now, targetInstant);

        long days = remainingDuration.toDays();
        long hours = remainingDuration.toHours() % 24;
        long minutes = remainingDuration.toMinutes() % 60;
        long seconds = remainingDuration.getSeconds() % 60;

        return new CountdownResponse(days, hours, minutes, seconds, targetInstant.toEpochMilli());
    }

    // Inner class to represent the response
    public static class CountdownResponse {
        private long days;
        private long hours;
        private long minutes;
        private long seconds;
        private long targetTimestamp;

        public CountdownResponse(long days, long hours, long minutes, long seconds, long targetTimestamp) {
            this.days = days;
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
            this.targetTimestamp = targetTimestamp;
        }

        public long getDays() {
            return days;
        }

        public long getHours() {
            return hours;
        }

        public long getMinutes() {
            return minutes;
        }

        public long getSeconds() {
            return seconds;
        }

        public long getTargetTimestamp() {
            return targetTimestamp;
        }
    }
}
