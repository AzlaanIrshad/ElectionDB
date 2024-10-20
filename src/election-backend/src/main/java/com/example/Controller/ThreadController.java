package com.example.controller;

import com.example.entity.Thread;
import com.example.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    @GetMapping("/threads")
    public ResponseEntity<List<Thread>> getThreads() {
        List<Thread> threads = threadService.getThreads();
        return ResponseEntity.ok(threads);
    }
}