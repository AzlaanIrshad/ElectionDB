package com.example.service;

import com.example.dto.LikeDTO;
import com.example.entity.Like;
import com.example.entity.Thread;
import com.example.entity.User;
import com.example.repository.LikeRepository;
import com.example.repository.ThreadRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all likes for a specific thread.
     *
     * @param threadId The ID of the thread.
     * @return List of likes.
     */
    public List<LikeDTO> getLikesByThread(Long threadId) {
        return likeRepository.findByThread(new Thread(threadId)).stream()
                .map(like -> new LikeDTO(
                        like.getVoteId(),
                        like.getVoteType().toString(),
                        like.getThread().getId(),
                        like.getUser().getId()
                ))
                .toList();
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

    /**
     * Create a like or dislike for a specific thread.
     *
     * @param threadId The ID of the thread.
     * @param voteType The type of vote (UPVOTE or DOWNVOTE).
     * @param userId   The ID of the user casting the vote.
     * @return The created like object.
     */
    public Like createVote(Long threadId, Like.VoteType voteType, Long userId) {
        Optional<Thread> threadOpt = threadRepository.findById(threadId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (threadOpt.isEmpty()) {
            throw new IllegalArgumentException("Thread not found with ID: " + threadId);
        }
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        Thread thread = threadOpt.get();
        User user = userOpt.get();

        Optional<Like> existingLike = likeRepository.findByThreadAndUser(thread, user);
        if (existingLike.isPresent()) {
            Like like = existingLike.get();
            like.setVoteType(voteType);
            return likeRepository.save(like);
        }

        Like like = new Like(user, voteType, thread);
        return likeRepository.save(like);
    }
}
