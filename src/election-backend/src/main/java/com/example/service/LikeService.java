package com.example.service;

import com.example.entity.Like;
import com.example.entity.Thread;
import com.example.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    /**
     * Get all likes for a specific thread.
     *
     * @param threadId The ID of the thread.
     * @return List of likes.
     */
    public List<Like> getLikesByThread(Long threadId) {
        return likeRepository.findByThread(new Thread(threadId));
    }

    /**
     * Count the number of votes by type (UPVOTE or DOWNVOTE) for a specific thread.
     *
     * @param threadId The ID of the thread.
     * @param voteType The type of vote (UPVOTE or DOWNVOTE).
     * @return The count of votes.
     */
    public int countVotesByType(Long threadId, Like.VoteType voteType) {
        return (int) likeRepository.findByThread(new Thread(threadId)).stream()
                .filter(like -> like.getVoteType() == voteType)
                .count();
    }
}
