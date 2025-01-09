package com.example.repository;

import com.example.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndThreadId(Long userId, Long threadId);

    /**
     * Telt het totaal aantal likes (upvotes of downvotes) op een thread.
     *
     * @param threadId De ID van de thread.
     * @param voteType Het type stem (UPVOTE of DOWNVOTE).
     * @return Het aantal stemmen van dit type.
     */
    long countByThreadIdAndVoteType(Long threadId, Like.VoteType voteType);
}