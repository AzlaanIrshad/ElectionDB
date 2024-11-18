package com.example.controller;

import com.example.dto.ThreadRequest;
import com.example.entity.Thread;
import com.example.entity.ThreadComment;
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

    @GetMapping("/threads/{id}")
    public ResponseEntity<Thread> getThread(@PathVariable Long id) {
        Thread thread = threadService.getThreadById(id);
        if (thread != null) {
            return ResponseEntity.ok(thread);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/threads")
    public ResponseEntity<Thread> createThread(@Valid @RequestBody ThreadRequest threadRequest) {
        Thread newThread = threadService.createThread(
                new Thread(threadRequest.getTitle(), threadRequest.getBody(), threadRequest.getDate(), threadRequest.getUser()),
                threadRequest.getCategories()
        );
        return ResponseEntity.ok(newThread);
    }

    @GetMapping("/threads/{id}/comments")
    public ResponseEntity<List<ThreadComment>> getComments(@PathVariable Long id) {
        List<ThreadComment> comments = threadService.getComments(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/threads/{id}/comments")
    public ResponseEntity<ThreadComment> createComment(@PathVariable Long id, @Valid @RequestBody ThreadComment comment) {
        ThreadComment newComment = threadService.createComment(id, comment);
        return ResponseEntity.ok(newComment);
    }
}
