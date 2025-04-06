package com.fatec.agendify.agendify.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class DateTestController {

    @GetMapping("/now")
    public Map<String, Object> getCurrentTime() {
        Map<String, Object> response = new HashMap<>();
        response.put("instant", Instant.now());
        response.put("localDateTime", LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return response;
    }

@GetMapping("/test-time")
public Map<String, Object> testTime() {
    Instant now = Instant.now(); 
    LocalDateTime local = LocalDateTime.now(); 
    ZonedDateTime zdt = ZonedDateTime.ofInstant(now, ZoneId.of("America/Sao_Paulo"));

    return Map.of(
        "instant", now,
        "localDateTime", local,
        "zonedDateTime", zdt
    );
}


}
