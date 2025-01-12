package com.example.controller;

import com.example.entity.Like;
import com.example.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * Get all likes for a specific thread.
     *
     * @param threadId The ID of the thread.
     * @return List of likes.
     */
    @GetMapping("/thread/{threadId}")
    public ResponseEntity<List<Like>> getLikesByThread(@PathVariable Long threadId) {
        List<Like> likes = likeService.getLikesByThread(threadId);
        return ResponseEntity.ok(likes);
    }

    /**
     * Get the summary of upvotes and downvotes for a specific thread.
     *
     * @param threadId The ID of the thread.
     * @return A map containing the count of upvotes and downvotes.
     */
    @GetMapping("/thread/{threadId}/summary")
    public ResponseEntity<Map<String, Integer>> getVoteSummary(@PathVariable Long threadId) {
        int upvotes = likeService.countVotesByType(threadId, Like.VoteType.UPVOTE);
        int downvotes = likeService.countVotesByType(threadId, Like.VoteType.DOWNVOTE);

        return ResponseEntity.ok(Map.of(
                "upvotes", upvotes,
                "downvotes", downvotes
        ));
    }
}
