package com.example.controller;

import com.example.dto.ThreadCommentRequest;
import com.example.dto.ThreadRequest;
import com.example.entity.Thread;
import com.example.entity.ThreadComment;
import com.example.service.ThreadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
                threadRequest.getTitle(), threadRequest.getBody(), threadRequest.getDate(),
                threadRequest.getCategories(), threadRequest.getEmail()
        );
        return ResponseEntity.ok(newThread);
    }

    @GetMapping("/threads/{id}/comments")
    public ResponseEntity<List<ThreadComment>> getComments(@PathVariable Long id) {
        List<ThreadComment> comments = threadService.getComments(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/threads/{id}/comments")
    public ResponseEntity<ThreadComment> createComment(@PathVariable Long id, @Valid @RequestBody ThreadCommentRequest threadCommentRequest) {
        ThreadComment newComment = threadService.createComment(id, threadCommentRequest);
        return ResponseEntity.ok(newComment);
    }
}
