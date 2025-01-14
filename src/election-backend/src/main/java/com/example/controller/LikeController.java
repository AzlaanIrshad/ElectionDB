package com.example.controller;

import com.example.dto.LikeDTO;
import com.example.entity.Like;
import com.example.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/likes")
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<List<LikeDTO>> getLikesByThread(@PathVariable Long threadId) {
        List<LikeDTO> likes = likeService.getLikesByThread(threadId);
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


    @PostMapping("/thread/{threadId}/vote")
    public ResponseEntity<Like> createVote(@PathVariable Long threadId,
                                           @RequestBody Map<String, Object> payload) {
        String voteType = (String) payload.get("voteType");
        Long userId = Long.valueOf((Integer) payload.get("userId"));

        try {
            Like like = likeService.createVote(threadId, Like.VoteType.valueOf(voteType), userId);
            return ResponseEntity.ok(like);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/thread/{threadId}/vote")
    public ResponseEntity<Void> removeVote(@PathVariable Long threadId,
                                           @RequestParam String voteType,
                                           @RequestParam Long userId) {
        try {
            likeService.deleteVote(threadId, Like.VoteType.valueOf(voteType), userId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

}
